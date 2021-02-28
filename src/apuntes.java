

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


- Alternativamente, se puede optar por crear una función estatica en la SecondActivity agrupando todo lo necesario para ejecutar
  satisfactoriamente un intent.

    fun getIntent(context: Context, saludo : String) Intent{

        val intent = Intent(context, SecondActivity :: class.java)

        intent.putExtra(CLAVE_1,saludo)

        return intent

    }

- Alternativamente siendo llamado desde MainActivity

    startActivity(SecondActivity.getIntent(this, "Hola, ¿que tal?"))







- Pasando clases de ActivityMain a SecondActivity I

Dentro de los intent solo podemos pasar cierto tipo de datos (Int, String, ect.).

Uno de esos tipos se denomina parcelable. Nuestro objeto

 */

