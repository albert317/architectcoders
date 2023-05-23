# Code Challenge Yape - Mobile Developer

Aplicación de Lugares Turísticos

1. Según la ubicación GPS del usuario, la aplicacion mostrará una lista de lugares turísticos del pais.
2. Al seleccionar un lugar, la aplicación una segunda pantalla con el detalle del lugar: Nombre, descripción, imagen, ubicación.
3. En el detalle del lugar, se podrá guardar en favoritos.
4. Se podrá realizar comentarios de cada lugar turistico.

Datos Técnicos:

1. Arquitectura: Clean Architecture  (Presentación, UseCase, Domain, Data)
2. MVVM 
3. Retrofit para el consumo de la API (lista lugares turisticos y detalle)
4. ROOM para almacenar los lugares turisticos y favoritos, patron repository
5. Real Time Data Base - Firebase, para almacenar y leer los comentarios realizados por el usuario, patron repository
6. Pruebas unitarias de integracion con Junit y Mockito
7. Pruebas de UI, con expresso.
8. Dagger Hilt para la inyección de dependencias.
