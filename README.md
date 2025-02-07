![Citas Médicas App](https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.XbExYTIVctsL_Y1yiqC89gAAAA%26pid%3DApi&f=1&ipt=f928e1a87416bacbe797547d6e70d4d628dc5b65c9623b522ec9890a98c32d4b&ipo=images)

# Citas Médicas App  
**Desarrollado por Luis Ubillus - 3er Semestre TSDS**  

## 📌 Descripción  
La aplicación **Citas Médicas App** permite gestionar citas médicas de manera eficiente. A través de una interfaz intuitiva, los usuarios pueden registrarse como **paciente, administrador o doctor** mediante un selector de opciones (*Picker View*).  

Cada tipo de usuario tiene permisos y funcionalidades específicas:  
- **Administrador**: puede ver todas las citas, crear, modificar y eliminar citas de cualquier usuario registrado.  
- **Paciente**: solo puede ver, editar y eliminar sus propias citas médicas.  

La aplicación está desarrollada siguiendo la arquitectura **MVVM (Model-View-ViewModel)** y utiliza **Navigation Component** para la gestión de fragmentos.  

## 🎯 Características Principales  
✅ **Registro de usuarios**: pacientes, doctores y administradores.  
✅ **Gestión de citas**: creación, edición y eliminación de citas.  
✅ **Interfaces personalizadas** según el tipo de usuario.  
✅ **Almacenamiento local con Room** para guardar citas de forma segura.  
✅ **Uso de RecyclerView** para visualizar citas médicas en listas organizadas.  
✅ **Navegación eficiente con Navigation Component** y un **Graph de navegación**.  
✅ **Manejo de asincronía** para mejorar el rendimiento.  

## 🛠️ Tecnologías Utilizadas  
- **Lenguaje de programación:** Kotlin  
- **Nivel de API:** 27  
- **Base de datos:** Room (Jetpack)  
- **Arquitectura:** MVVM  
- **Interfaz de usuario:** XML + Jetpack Components  

## 📂 Estructura del Proyecto  
La aplicación sigue una organización modular para mejorar la mantenibilidad y escalabilidad:  

### 📁 `data` (Modelo - Persistencia de Datos)  
📌 **Base de datos Room** para almacenar información localmente.  
📌 **DAO (Data Access Object)** para operaciones CRUD en la base de datos.  
📌 **Modelos de datos** como `Appointment`, `Doctor`, `User`, etc.  

### 📁 `ui` (Vista - Interfaz de Usuario)  
📌 **Pantallas principales** organizadas en fragmentos.  
📌 **Adapters para RecyclerView**, facilitando la visualización de citas.  
📌 **Uso de Navigation Component** para cambiar entre fragmentos.  
📌 **Material designer** Reutilizacion de de codigo mediante recursos xml string, fot, size etc.  

### 📁 `viewmodel` (Lógica de Negocio)  
📌 **Gestión de datos entre la UI y la base de datos.**  
📌 **Manejo de LiveData y ViewModel** para evitar problemas de ciclo de vida.  

This app provides a simple and user-friendly interface for managing bank accounts efficiently. It is designed to meet essential appointment medical.
