package com.app.featureplaces.presentation.data.firebase

import android.util.Log
import arrow.core.Either
import com.app.featureplaces.data.datasource.CommentFirebaseRemoteDataSource
import com.app.domain.Comment
import com.app.domain.Error
import com.app.featureplaces.presentation.data.tryCall
import com.app.featureplaces.presentation.di.ApiKey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CommentFirebaseServerDataSource @Inject constructor() :
    CommentFirebaseRemoteDataSource {
    override suspend fun getCommentsOfPlace(idPlace: Int): Either<Error, Flow<List<Comment>>> =
        tryCall {
            callbackFlow {
                val databaseFirebase =
                    Firebase.database.reference.child("comments").child(idPlace.toString())
                val listener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val listComments = ArrayList<RemoteComment>()
                        snapshot.children.map { postSnapshot ->
                            postSnapshot.getValue(RemoteComment::class.java)
                                ?.let { listComments.add(it) }
                        }
                        listComments.sortByDescending { it.timeRegister }
                        Log.d("COMMENTS", Gson().toJson(listComments))
                        trySend(listComments.toDomain())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        trySend(ArrayList())
                    }
                }
                databaseFirebase.addValueEventListener(listener)
                awaitClose { databaseFirebase.removeEventListener(listener) }
            }
        }

    override fun saveCommentOfPlace(comment: Comment): Flow<Error?> =
        callbackFlow {
            val databaseFirebase = Firebase.database
            val commentReference =
                databaseFirebase.getReference("comments").child(comment.idPlace.toString())
            commentReference.push().setValue(comment).addOnSuccessListener {
                trySend(null)
            }.addOnCanceledListener {
                trySend(Error.Unknown("error"))
            }
        }


    private fun RemoteComment.toDomain() = Comment(
        id,
        idPlace,
        idUser,
        timeRegister,
        nameUser,
        commentText
    )

    fun List<RemoteComment>.toDomain() = map { it.toDomain() }

    private fun Comment.fromDomainModel() = RemoteComment(
        id,
        idPlace,
        idUser,
        timeRegister,
        nameUser,
        commentText
    )
}
