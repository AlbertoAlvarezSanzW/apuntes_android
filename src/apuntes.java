

/*

Deficiones


onCreate: Se ejecuta cuando se crea una Activity por primera vez

onStart: Se ejecuta despues del onCreate cuando se crea por primera vez o cuando una Antivity ha sido sacada de memoria y quiere
volverse a mostrar.

onResume: Se ejecuta despues del onStart o cuando una Activity que estaba en segundo plano vuelve a estar en primer plano.

onPause: Se ejecuta cuando el usuario manda una Activity al segundo plano.

onStop: Se ejecuta cuando la Activity deja de ser visible.

onDestroy: Se ejecuta cuando el usuario elimina la Activity del segundo plano o Android requiere memoria. No siempre se ejecuta.



onSaveInstanceState I

metodo para cuando una activity va a destruirse con perspectivas de ser recreada, entonces se llama al metodo:
    onSaveInstanceState(outState: Bundle)
En outState es posible guardar valores.
    outState.putString(TAG_USER_TEXT, etText.text.toString())
Siendo TAG_USER_TEXT un String




onSaveInstanceState II

Cuando se vuelve a ejecutar el método onCreate(saveInstanceState: Bundle?) se recibirán los valores guardados en el outState dentro de savedInstanceState.

Para recuperar los valores guardados anteriormente, utilizamos el siguiente código.

savedInstanceState?.run{
    getString(TAG_USER_TEXT)?.let {
        etText.setText(it)
    }
}


sharedPreferences I

La forma mas sencilla de almacenar datos de forma persistente es utilizando una API sharedPreferences. Se puede accedes a ella a través de un Context
y permite almacenar duplas de clave valor (similar a lo que se ha realizado en onSaveInstanceState).

Para acceder a las sharedPreferences se debe ejecutar el siguiente codigo:

    val sharedPref = getPreferences(Context.MODE_PRIVATE)


sharedPreferences II

Para guardar un valor en las que las sharedPreferences debes:

with (sharedPref.edit()){
    putString (TAG_USUARIO, string)
    commit()}

para guardar un valor:

return sharedPref.getString(TAG_USUARIO, "")

Jetpack DataStore   --> actua de diccionario almacena clave: valor


Toast

Una forma sencilla de dar feedback al usuario es mediante los Toast.
Un Toast es una ventana emergente de cirta duración capaz de mostrar textos.







                                                    Tema 5

Accediento / Modificando los atributos de una vista:

Es posible acceder al contenido de una vista utilizando los respectivos metodos disponibles:

    ojooo !!!! <--- depende del tipo de vista

    twContent.text = etContent.text

    siendo

    twContent un TextView y etContent un EditText



Un listener es una función que se queda en espera hasta que cierto evento ocurre, momento en el que se ejecuta. Ejemplo:

    b.Convert.setOnClickListener {...}
    twContent.setOnLongClickListener {...}                      <-- devuelve un booleano
    etContent.addTextChangedListener( object : TextWatcher {
        override fun afterTextChanged (s: Editable?) {...}
        override fun beforeTextChanged (s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged (s: CharSequence?. start: Int, before: Int, count: Int) {}})



Adapters en Android

La forma mas eficiente de mostrar una gran cantidad de elementos similares es utilizando un adapter. Serán necesarios 3 paos:

    - Definir el RecyclewView, donde ubicaremos todos los elementos dentro de la UI
    - Definir un ViewHolder, donde gestionaremos la UI de cada elemento
    - Crear un Adapter, donde organizaremos al conjunto de viewHolter


Adapters con listas I
    La forma mas eficiente de mostrar listas en Android:

    Adapter:

    class StringAdapter(var stringList : List < String >):
    RecyclerView.Adapter<StringAdapter.StringViewHolter>()


ViewModelProvinder -> en vez de pasarle la actividad le estas pasando de quien es dueño el viewmodel y el dueño del viewmodel es el main activity
de esta manera es mas comodo para destruir y volver a cargar sin necesidad de volver a crearlo todo, tambien el no usar el viewmodel probocara acumulación
de codigo, por lo que es posible que haya sobrecarga


por lo tanto sacamos en limpio del viewmodel es: Separar el modulos las cosas,
 asi se tienes los fallos mas localizados
 actividades en 2º plano
 todo mas controlado
 rearlizar mucho mejor las llamadas a metodos en 2º plano


 Error comun: DefaultDispatcher <- es muy comun, solo el hilo original que ha creado una vista puede tocarlas, esto quiere decir que la vista
 del hilo del interfaz nos hemos ido a otro hilo y le estamos diciendo que cambie las vistas, y este error siempre que nos de es que no se puede hacer de esta manera


Para ejecutar ciertas tareas en segundo plano utilizaremos las corrutinas necesitamos ponerle el withcontex.main


Comunicación Adapter

* La comunicación entre la Activity y el Adapter es muy sencilla. Simplemente se crea una fimcion en el adapter que se llama desde la Activity-

    Ejemplo: adapter.addPreguntaToList(peregunta)

* La comunicación en sentido contratio Adapter - Activity no es tan sencilla ya que el Adapter no sabe de la existencia de la activity. Por ello, debemos
    utilizar callbacks o listener


    PASO 1
    * Definir una interfaz e implementarla en la Activity:

        interface MyListener {fun onSomeAction()}
        class MainActivity : AppCompatActivity(), MyListener {
        override fun onSomeAction() {...}
        }

    PASO 2
    *   Modificar nuestro adapter para recibir un parametro de tipo MyListener

        class PreguntaAdapter(var listener : MyListener) : RecyclewView.Adapter

    *   Pasar nbuestra activity como parametro al Adapter

        var adapter: PreguntaAdapter = PreguntaAdapter(this) // siendo this, nuestra Activity que implementar MyListener

    *   Llamar al listener desde nuestro Adapter

        listener.doSomeAction()








                                                Tema 6

BottomNavigationView es una de las formas de navegar dentro de la app mas comunes.
No necesita incorporar nada en el glade <---

Solo necesitamos crear un menu, añadir el BottomNavigationView



                                                Tema 7

                                ------> Nueva Activity II <------

class SecondActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)
    }
}
                                ------> Se actializa el manifest <------

<application

...

    <actuvity android:name = ".SecondActivity"/>

</application>



                                ------> Se modifica MainActivity <-----

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState : Bundle?){

        ...

        button.setOnClickListener{

            val intent = Intent(this, SecondActivity :: class.java)

            startActivity(intent)

        }

    }

}

                    ------> Pasando Datos de ActivityMain a SecondActivity <-----

        - La manera mas simple de enviar datos de una Activity a otra es enviar un intent desde MainActivity:

    val intent = Intent(context, SecondActivity:: class.java)

    intent.putExtra("Clave",saludo)

    startActivity(intent)

        - Recibiendo del siguiente modo:

    val saludo = intent.getStringExtra("Clave")



        - Por convenio, es mejor declarar la clave como un companion object en SecondActivity.

    companion object{

        const val CLAVE_1 = "ClaveSecondActivityString"

    }


        - Alternativamente, se puede optar por crear una función estatica en la SecondActivity agrupando
         todo lo necesario para ejecutar satisfactoriamente un intent.

    fun getIntent(context: Context, saludo : String) Intent{

        val intent = Intent(context, SecondActivity :: class.java)

        intent.putExtra(CLAVE_1,saludo)

        return intent

    }

        - Alternativamente siendo llamado desde MainActivity

    startActivity(SecondActivity.getIntent(this, "Hola, ¿que tal?"))







        - Pasando clases de ActivityMain a SecondActivity I

Dentro de los intent solo podemos pasar cierto tipo de datos (Int, String, ect.).
Uno de esos tipos se denomina parcelable. Nuestro objeto será convertir nuestras
clases en objetivos Parcelables.

El proceso requiere incluir en granle de nuestra app lo siguiente:

    plugins{

        id 'kotlin-parcelize'

    }



es de ActivityMain a SecondActivity II

Las clases que queremos enviear en un intent, deberá incluir la siguiente etiqueta e impresión:


                                     --> Librerías <--

        - Durante los siguiente ejemplo se usarán las siguientes librerias que habrá que importar en el gradle:

    implementation 'com.squareup.okhttp3:okhttp:4.8.0' Para gestionar las conexiones HTTPS.
    implementation 'com.google.code.gson:gson:2.8.6' Para gestionar los JSON que se reciben de la API.
    implementation 'org.jetbrains.kotlinx-serialization-runtime:0.9.1' Para transformar los datos recibidos eficientemente.
    implementation 'org.jetbrains.kotlinx-corutines-android:1.3.9' Para gestionar donde se ejecutarán los distintos elementos que componen la App.

  Permisos

  Será necesario añadir los siguientes permisos a tu AndroidManifest:

  <uses-permission andriod:name="android.permission.INTERNET" />
  <uses-permission andriod:name="android.permission.ACCESS_NETWORK_STATE" />


        val client = OkHttpClient()
        val url = "nuestra api que nos vamos a conectar"
        val request = Request.Builder().url(url).build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {...}
            override fun onResponse(call: Call, response: Response) {...}



                                - Transformar en respuesta:

  val JsonObject = JSONObject(bodyInString)
  val results = JsonObject.optJSONArray("results")
  results?.let {
        val gson = Gson()
        val itemType = object : TypeToken<List<Film>>() {}.type
        val list = gson.fromJson<List<Film>>(results.toString(), itemType)

  }

                                    Motores de videojuegos:

    El “motor para videojuegos” es el nombre con el que se conoce a los programas gratuitos o de pago que las empresas o los creadores utilizan para crear videojuegos.

    Estos programas se basan en bibliotecas con funciones, clases y otros datos definidos por lenguajes de programación informática. Con estos datos ya creados por otra persona,
    un usuario puede utilizar el motor para videojuegos “x” para crear un nuevo videojuego. La principal ventaja que ofrecen los motores para videojuegos es que ahorran codificación
     desde un primer momento.

    En resumen, los motores para videojuegos permiten crear nuevos videojuegos con menos codificación, menos complicaciones y en mucho menos tiempo.

    A continuación te enumeramos algunos de los mejores motores para videojuegos y en qué videojuegos se utilizan:

    1. Unreal Engine

    Fue creado por Epic Games en 1998. En 2012 se presentó Unreal Engine 4, una nueva versión del motor. Este motor se utiliza para crear videojuegos de FPS, de estrategias y de carreras.
    Entre las empresas que lo utilizan se encuentran Electronic Arts y Ubisoft. Utiliza el lenguaje de programación C++. Epic Games permite que un gran número de personas pueda utilizar
    este motor a cambio de una tarifa fija.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        Batman: Arkham Asylum
        BioShock
        Gears of War
        Medal of Honor: Airborne
        Mortal Kombat vs. DC Universe

    2.- CryENGINE

    Este es el motor para videojuegos que utiliza Crytek, una empresa fundada por los hermanos Cevat Yerli, Avni Yerli y Faruk Yerli. CryENGINE ofrece algunos de los gráficos más potentes
    de todos los motores para videojuegos actualmente disponibles en el mercado.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        Far Cry
        Crysis
        Crysis 2

    3. Frostbite Engine

    Este motor para videojuegos creado por Digital Illusions CE se utiliza para crear videojuegos de acción en primera persona. Se presentó principalmente para la serie de videojuegos Battlefield.
    Ha jugado un papel fundamental en prácticamente todos los videojuegos de EA. La nueva versión del motor Frostbite Engine es Frostbite 3.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        The Battlefield Series
        Star Wars: Battlefront
        Need for Speed: The Run
        Need for Speed: Rivals
        Medal of Honor

    4. Anvil Engine

    El Anvil Engine fue creado por Ubisoft para el propio uso de la empresa. Aunque su nombre original era Scimitar, su nombre actual es Anvil Next. Este motor para videojuegos fue el primero
    que se utilizó para programar Assassin’s Creed. Es muy popular por los altos niveles de inteligencia artificial e interacción con el entorno que ofrece en sus videojuegos.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        The Assassin’s Creed Series
        Prince of Persia
        Tom Clancy’s Rainbow 6: Patriots

    5. Unity 3D

    Unity 3D es un motor para videojuegos de uso gratuito. Se trata de una de las innovaciones más importantes creadas por la comunidad científica y de videojuegos y permite jugar a complejos
    videojuegos en 3D sin necesidad de instalarlos en el ordenador. Los videojuegos creados con el motor Unity 3D se pueden jugar en un navegador con el reproductor Unity Web Player, eliminando
    la necesidad de instalar el videojuego.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        Battlestar Galactica Online
        Gone Home
        Hearthstone
        Una amplia gama de videojuegos para dispositivos móviles

    6. Source

    Source es un motor para videojuegos en 3D desarrollado por la empresa Valve Corporation. Se utilizó por primera vez en 2004 con el lanzamiento de Counter-Strike: Source y Half-Life 2 poco
    después.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        Counter Strike Source
        The Portal Series
        Left 4 Dead – Left 4 Dead 2
        Half Life 2

    7. Quake Engine



    La empresa id Software creó este motor Quake para el videojuego Quake. Desarrollado en 1995, probablemente allanó el camino para todos los motores actualmente disponibles en el mercado.
    El concepto de videojuego en 3D que emergió entre 1995 y 2000 nació realmente con el Quake Engine.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        Quake
        Quake Arena
        Half Life

    8. M.U.G.E.N

    M.U.G.E.N es un motor para videojuegos de lucha en 2D diseñado por la empresa Elecbyte que se utiliza desde 1999.

    Con este motor, cualquier persona tiene la posibilidad de crear personajes, escenas y otros objetos del videojuego utilizando una colección de archivos de texto con formato, gráficos y
    sonidos. El motor proporciona funciones como las de muchos videojuegos de lucha en 2D, como “Street Fighter” y “King of Fighters”.

    9. RAGE

    RAGE es un motor para videojuegos creado por la empresa de videojuegos Rockstar con el que pretende contribuir al éxito de Rockstar North.

    Rockstar Games desarrolló este motor para videojuegos para ordenador, así como para las consolas PlayStation 3, Wii y Xbox 360, con el objetivo de facilitar la producción de videojuegos.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        Midnight Club: Los Angeles
        Grand Theft Auto IV
        Red Dead Redemption

    10. HeroEngine

    HeroEngine es un motor para videojuegos en 3D y una plataforma de servidor especialmente desarrollada por la compañía Simutronics Corporation para la creación de videojuegos MMO. El motor
    se desarrolló para el videojuego de la empresa Hero’s Journey y ha sido galardonado con varios premios. A partir de aquí, muchas empresas adquirieron licencias de este motor para utilizarlas
    en sus propios videojuegos.

    Entre los videojuegos programados que utilizan este motor para videojuegos se incluyen los siguientes:

        Hero’s Journey
        Star Wars: The Old Republic


    Empezando con videojuegos
Areas para programar videojuegos.

En el desarrollo de videojuegos existen varia áreas, estas son las cinco principales: Diseño, Programación, Gráficos, Audio, Distribución y Marketing. Vamos a pasar a analizarlas brevemente.

    Diseño. La parte más importante de un videojuego. Historia, Guión, jugabilidad, reglas y demás conceptos que hacen a un juego ser lo que es.
    Programación. Una vez elaborado un diseño es la parte donde se juntan gráficos, audios y reglas para dar vida a un mundo interactivo. Existen varias disciplinas a su vez dentro de ella como
    programación gráfica, gameplay o inteligencia artificial.
    Gráficos. Interfaces, modelos 3D, animaciones y todo lo que “se ve” de el videojuego, existen varias disciplinas tanto en 2D como en 3D.
    Audio. Efectos de sonido, música de fondo, diálogos. Muy importante para crear ambiente.
    Distribución y marketing. El arte de publicar y promocionar un videojuego, responsable del éxito o no de muchos productos dependiendo de las estrategias que sigan.

Los grandes estudios tienes varias decenas de programadores, especializados en diferentes áreas. En programación de videojuego los principales equipos y/o disciplinas que puedes encontrar son
los siguientes.

    - Programación del motor. Son los encargados de implementar la base sobre la que se sustenta el videojuego. Comunicación con el sistema operativo, gestión de memoria, gestión de cadenas,
    gestión de recursos, etc. Son necesarios grandes conocimiento de la plataforma para la que se programa, algoritmia y complejidad, opimización y gestión a bajo nivel.
    - Programación gráfica. Su misión es lidiar con las diferentes apis gráficas como DirectX y OpenGL. Conocimienos de dichas apis, y matemáticas sobre todo álgebra y geometría.
    - Programación de física. Se encarga de emular los comportamientos físicos del videojuego. Conocimientos de matemática vectorial y física dinámica y mecánica.
    - Programación de inteligencia artificial. Es la encargada de hacer nuestros enemigos (o nuestros aliados) inteligentes. Conocimientos de lenguajes de script, matemáticas y algoritmos de
    IA como pathfinding, máquinas de estados finitos o redes neuronales.
    - Programación de red. Se encarga de la parte multijugador, servidores y todo lo que sea conectar una máquina con otra.
    - Programaición de Gameplay. El equipo que se encarga de programar la lógica del juego, sus reglas. Conocimientos de lenguajes de script y uso de las partes desarrolladas por los otros equipos.

Estas son las principales áreas en las que se dividen los grandes estudios pudiendo variar en muchos de ellos, pero estas son las principales disciplinas de la programación de videojuegos.
Vale, lo tengo claro, ¿Por cual empiezo? Por todas y por ninguna.

Cuando uno empieza ninguna de las áreas tiene un alto grado de complejidad y el programador indie debe aprender a lidiar con todas las áreas, las especializaciones es bueno tenerlas en cuenta
de cara a un futuro, pero para empezar te tocará aprender un poco de todo.

Conocimientos básicos necesarios para programar videojuegos

    - Conocimintos de matemáticas. En principio no son muchos y dependerán básicamente del tipo de juego, pero suelen ser esenciales conocimientos básicos de trigonometría y geometría.
    - Conocimintos de física. Como las matemáticas depende del tipo de juego, para juegos de plataforma con conocimientos básicos de cinemática es suficiente.
    - Conocimintos de programación. Se debe saber programar y conocer bien un lenguaje de programación el lenguaje elegido es lo de menos siempre que sea popular y con una amplia comunidad y
    colección de bibliotecas.

Si se poseen estos conocimientos lo siguiente es buscar una biblioteca para el desarrollo de videojuegos de tu lenguaje. Aquí van algunas de las para los lenguajes más populares.

    C: SDL
    C++: SFML
    C#: XNA / MonoGame
    Python: PyGame
    Java: libgdx, spiller
    Ruby: Gosu
    Flash: Flixel
    Lua: Love2D

El lenguaje es lo de menos en todos existen buenas bibliotecas 2D para empezar a desarrollar videojuegos. Lo importante es aprender las técnicas de la programación en tiempo real y eso es
aplicable a cualquier lenguaje.






                              --- ¿Qué es Unity?. Instalación y configuración de Unity. ---

  ¿Qué es Unity?

Es una plataforma preparada para el desarrollo de videojuegos en 3D y 2D. Lo más destacado es que gran parte del desarrollo se hace sin programar,
solo poniendo muñecos, fondos y objetos en la pantalla, al estilo arrastrar y soltar.

Descarga e instalación de Unity 3D.

https://unity3d.com/es/get-unity/download/archive

Descargaremos la versión Unity 2018.3.4 para el sistema operativo correspondiente.

El instalador nos preguntará si queremos instalar:

    Unity <- Debemos instalarlo por motivos obvios
    Web Player <- Si queremos ver juegos en 3D en el navegador. Es opcional.
    Standard Assets <- Recursos estándar para desarrollar juegos. Opcional, pero recomendable por tener recursos con que empezar a programar
    Example project <- Un proyecto de ejemplo con los recursos estándar

Una vez finalizada la instalación, nos preguntará por usuario y clave. Es necesario registrarse

Al arrancar la sesión podremos navegar entre tutoriales a través de la pestaña "Learn" y la pestaña "projects".

La primera nos guiará a través de una serie de tutoriales y la pestaña "Projects" nos permitirá crear nuevos proyectos.



Creación de proyectos en Unity

Creación de un nuevo proyecto.

1.- Projects -> New Project
2.- En la siguiente ventana, crearemos un proyecto 2D denominado por ejemplo UnityProject1.
En este punto se descargarán nuevos paquetes si es necesario.

La pantalla se divide en diversas zonas de trabajo (las llamaremos ventanas) organizadas de una determinada forma (Layout). Básicamente la pantalla está formada por estas

Ventanas:
    Scene: Es donde se desarrolla el juego, la escena, ahí añadiremos los sprites y recursos para que interactúen entre sí. Los veremos en formato gráfico
    Hierarchy: Ahí nos saldrán todos los sprites, objetos y recursos que añadamos a la escena pero en forma jerárquica. Los veremos en formato texto.
    Inspector: cuando seleccionamos un objeto desde Scene o desde Hierarchy aquí nos saldrá su detalle con sus características: posicion, tamaño, etc.
    Project: Aquí veremos los recursos (Assets) disponibles del juego.
    Botones de simulación: Son los que nos permiten echar a andar nuestro mundo o pausarlo. Si le damos al play iniciaremos el juego

En la ventana Project tendremos los recursos disponibles para añadir cuantos queramos a la escena, por ejemplo, podemos tener un recurso Coche, si lo añadimos a la escena
 10 veces tendremos 10 coches, cada uno con sus características propias (posicion, tamaño, etc) y todos compartiendo características comunes (textura por ejemplo).
Para cambiar las distintas zonas de ventanas de otra forma (de otro Layout) podemos hacerlo arrastrándolas o escogiendo alguna de las distintas Layout predefinidas que
 pueden escogerse, cargarse y grabarse pinchando en el botón que pone Layout.
En la parte de Assets es donde están los recursos que vamos a usar en nuestro juego, ahora esta zona estará vacía pero podemos rellenarla con imágenes si añadimos los
sprites.

3.- Añadir imágenes al proyecto.

Antes de pasar los recursos a nuestro proyecto Unity hay que organizar bien las carpetas. Luego se pasa fácilmente pinchando con el botón derecho en Assets y seleccionando
 Show in Explorer, entramos en la carpeta Assets y copiamos ahí los recursos. Si volvemos a Unity veremos que nuestra carpeta de Assets ya no está vacía, tendrá
 todo lo que hemos copiado con la misma estructura.

En nuestro caso tenemos una escena de ejemplo: SampleScene.

También tenemos la posibilidad de usar la tienda de recursos (Window->Asset Store o pulsando Ctrl+F9). Hay un buscador si ponemos 2D encontraremos recursos 2D, Le damos
al botón de download (o import) y tras un par de segundos nos saldrá una ventana donde nos dice lo que nos va a bajar.

Modo de Edición en Unity

En el modo de edición los cambios que se hagan en nuestra escena y proyecto serán permanentes. Este modo es el normal de trabajo. Para saber si estamos en este modo
simplemente hay que mirar que el botón play no esté pulsado (está de color negro)

Modo de ejecución en Unity

Se activa pulsando en el botón play (se volverá azul). En el modo de ejecución los cambios son temporales, este modo se usa para realizar cambios "al vuelo" y ver
cómo va quedando la escena. Pero al volver al modo edición (pulsando play otra vez) se perderán los cambios, por ello hay que volverlos a añadir. Este modo se usa para
hacer pruebas e ir ajustando el juego

Descarga de Sprites

Tras escoger la temática necesitamos los gráficos de los personajes (sprites) y los de los fondos.

Podemos hacerlos nosotros mismos o comenzar descargándolos de la web. Entre los muchos recursos existentes podemos encontrar:

- the spriter resource

- vxresource

- opengameart

- GameDev

En la web de Unity hay también una sección para comprar y descargar recursos de videojuegos.

Para sonidos podemos utilizar Freesound.

Uso de Sprites


 */

