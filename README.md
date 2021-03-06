# Securing Intents
En esta practica vamos a aprender a proteger los Intent Implicitos de aplicaciones que podrian contener informacion sensible y comprometerla.

### Objetivos de la practica
1. Crear un login sencillo para fines del ejemplo
2. Crear Activities y sus respectivos Layouts
3. Conocer los IPC que nos proporciona Android.
4. Implementar un Intent implicito y ser capaces de protegerlo.
5. Construir un modulo interno el cual interceptara la informacion del Intent usado en la practica

### Paso 1 - Crear un proyecto Android y utilizando el wizard crear una activity de nombre LoginActivity y el nombre del fichero xml sera activity_login.xml
Agregamos el siguiente codigo al fichero activity_login.xml:
```xml
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        tools:context="com.example.rodryvazquez.buildlogionapp.LoginActivity">

        <TextView
            android:id="@+id/txtLoginAppTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Build Login App"
            android:layout_alignParentTop="true"
            android:textSize="22dp"
            android:fontFamily="sans-serif-condensed"
            android:textStyle="bold"
            android:padding="20dp"
            android:gravity="center_horizontal"/>

        <TextView
            android:id="@+id/txtUserNameLabel"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:text="User Name"
            android:textSize="18dp"
            android:padding="10dp"
            android:fontFamily="sans-serif-condensed"
            android:textStyle="bold"
            android:layout_alignParentLeft="true"
            android:layout_below="@+id/txtLoginAppTitle"/>

        <EditText
            android:id="@+id/edtInputUserName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="14dp"
            android:fontFamily="sans-serif-condensed"
            android:hint="enter user name"
            android:layout_below="@+id/txtUserNameLabel"/>

        <TextView
            android:id="@+id/txtPasswordLabel"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Password"
            android:textSize="18dp"
            android:padding="10dp"
            android:fontFamily="sans-serif-condensed"
            android:textStyle="bold"
            android:layout_alignParentLeft="true"
            android:layout_below="@+id/edtInputUserName"/>

        <EditText
            android:id="@+id/edtInputPassword"
            android:inputType="textPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="14dp"
            android:fontFamily="sans-serif-condensed"
            android:hint="enter password"
            android:layout_below="@+id/txtPasswordLabel"/>

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Submit"
            android:onClick="submitInfo"
            android:layout_centerHorizontal="true"
            android:layout_below="@id/edtInputPassword" />

    </RelativeLayout>

```
Lo siguente es ir a LoginActivity y agregaremos el siguiente codigo:

```java
        /**
         *
         */
        EditText edtUserName, edtPassword;

        /**
         *
         * @param savedInstanceState
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            edtUserName = (EditText)findViewById(R.id.edtInputUserName);
            edtPassword = (EditText) findViewById(R.id.edtInputPassword);
        }

        /**
         *
         * @param v
         */
        public void submitInfo(View v) {
            Intent intent = new Intent();
            intent.putExtra(ApplicationConstants.KEY_USERNAME,edtUserName.getText().toString());
            intent.putExtra(ApplicationConstants.KEY_PASSWORD,edtPassword.getText().toString());
            intent.setAction(ApplicationConstants.KEY_INTENT_ACTION_LOGIN);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }
```
Notaras que al utilizar el wizard el metodo onCreate se agrego automaticamente por lo que agregaremos solo las referencias de los EditText y el metodo submitInfo

### Paso 2 - Dentro del paquete dar click derecho seleccionar New => Package y nombrarlo Helpers, una vez creado Helpers dar click derecho y crear una clase de nombre ApplicationConstants
La estructura de ApplicationConstants quedara de la siguiente manera:
```java
    public class ApplicationConstants {

        // region IntentReceiverActivity

        public static final String TAG_RECEIVER_ACTIVITY = "IntentReceiverActivity";
        //endregion

        // region Common

        public static final String KEY_PASSWORD = "password";
        public static final String KEY_USERNAME = "username";
        public static final String KEY_INTENT_ACTION_LOGIN = "TestLogin";
        //endregion
    }
```
---
# Hasta este punto ya tenemos un proyecto que aunque no esta terminado ya lo podemos correr
---

### Paso 3 - A nivel del paquete de la aplicacion dar click derecho New => Activity => EmptyActivity y nombrarla IntentReceiverActivity y al fichero xml activity_intent_receiver.xml
Agregar al fichero activity_intent_receiver.xml el siguiente codigo:

```xml
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="com.example.rodryvazquez.buildlogionapp.IntentReceiverActivity">

        <TextView
            android:id="@+id/txtUserNameReceiverLabel"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="UserName:"
            android:textSize="22dp"
            android:fontFamily="sans-serif-condensed"
            android:layout_marginLeft="20dp"
            android:textStyle="bold"/>

        <TextView
            android:id="@+id/txtUserNameReceiverValue"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=""
            android:textSize="18dp"
            android:fontFamily="sans-serif-condensed"
            android:textStyle="normal"
            android:layout_marginLeft="5dp"
            android:layout_alignParentRight="true"
            android:layout_toRightOf="@+id/txtUserNameReceiverLabel" />


        <TextView
            android:id="@+id/txtPasswordLabelReceiverLabel"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Password:"
            android:textSize="22dp"
            android:fontFamily="sans-serif-condensed"
            android:layout_marginLeft="20dp"
            android:layout_below="@+id/txtUserNameReceiverLabel"
            android:textStyle="bold"/>

        <TextView
            android:id="@+id/txtPasswordReceiverValue"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=""
            android:textSize="18dp"
            android:fontFamily="sans-serif-condensed"
            android:textStyle="normal"
            android:layout_marginLeft="5dp"
            android:layout_below="@+id/txtUserNameReceiverValue"
            android:layout_alignParentRight="true"
            android:layout_toRightOf="@+id/txtUserNameReceiverLabel" />

    </RelativeLayout>
```
La Activity IntentReceiverActivity quedaria de la siguiente manera:

```java
        TextView txtUserNameLabel, txtPasswordLabel;

        /**
         *
         * @param savedInstanceState
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intent_receiver);

            txtUserNameLabel = (TextView)findViewById(R.id.txtUserNameReceiverValue);
            txtPasswordLabel = (TextView)findViewById(R.id.txtPasswordReceiverValue);

            //Obtenemos los datos del intent
            Bundle bundle = getIntent().getExtras();
            String result1 = bundle.getString(ApplicationConstants.KEY_USERNAME);
            String result2 = bundle.getString(ApplicationConstants.KEY_PASSWORD);

            //Mostramos las credenciales en el layout
            txtUserNameLabel.setText(result1);
            txtPasswordLabel.setText(result2);

            Log.d(ApplicationConstants.TAG_RECEIVER_ACTIVITY,result1);
            Log.d(ApplicationConstants.TAG_RECEIVER_ACTIVITY,result2);
        }
```

Como podemos observar la Activity IntentReceiverActivity es la encargada de recibir la informacion desde el login pero para que se pueda comunicar con la Activity que contiene el login es necesario registrar el filtro del intent en el manifiesto de la aplicacion.

El manifiesto quedaria de la siguiente manera:

```xml
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.example.rodryvazquez.buildlogionapp">

        <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">

            <!-- Login -->
            <activity
                android:name=".LoginActivity"
                android:theme="@style/Theme.AppCompat.Light.NoActionBar">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN" />
                    <category android:name="android.intent.category.LAUNCHER" />
                </intent-filter>
            </activity>
            <!-- Receiver -->
            <activity android:name=".IntentReceiverActivity">
                <!-- Asociamos la accion y categoria en el intent-filter -->
                <intent-filter>
                    <action android:name="TestLogin"/>
                    <category android:name="android.intent.category.DEFAULT"/>
                </intent-filter>
            </activity>
        </application>
    </manifest>
```
---
# Listo. Hasta este punto hemos conseguido la comunicacion entre LoginActivity e IntentReceiverActivity y notaras que al hacer submit al formulario se abrira IntentReceiverActivity y mostrara los campos de nombre de usuario y contraseña
---
Lo siguiente que tendremos que hacer es crear un modulo dentro de la misma aplicacion  el cual contendra otra aplicacion Android misma interceptara la informacion enviada desde LoginActivity a IntentReceiverActivity.
### Paso 4 - Dar click derecho a nivel proyecto  y seleccionar New => Module => Phone & Tablet Module, le llamaremos HackApp al igual que en los pasos anteriores crearemos una Activity de nombre MainActivity y su fichero xml de nombre activity_main.xml

El codigo de MainActivity quedaria de la siguiente manera:
```java
     @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Obtenemos los datos del intent
            String message = "Username: " +
                    this.getIntent().getStringExtra("username") +
                    "\n Password: " +
                    this.getIntent().getStringExtra("password");
            //Mostramos el dialogo
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Sensitive data was accessed")
                    .setMessage(message)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.create().show();
        }
```
y por ultimo registramos el filtro del intent en el manifiesto de HackApp
```xml
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.example.hackapp">

        <application android:allowBackup="true" android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name" android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true" android:theme="@style/AppTheme">
            <activity android:name=".MainActivity">
                    <!-- Asociamos la accion y categoria en el intent-filter -->
                    <intent-filter>
                        <action android:name="TestLogin"/>
                        <category android:name="android.intent.category.DEFAULT"/>
                    </intent-filter>
            </activity>
        </application>

    </manifest>
```
Notaras que al intentar correr el proyecto HackApp no podra iniciar porque no hemos declarado en su manifiesto que actividad sera la principal, por lo que seleccionaremos EditConfigurations seleccionaremos HackApp y en Launch pulsaremos la opcion Nothing.
Bien ahora viene lo intesante si corremos la aplicacion la aplicacion que contiene nuestro Login y hacemos submit de la informacion podremos observar que nos aparece una pantalla que Android nos proporciona las opciones de IntentReceiverActivity y MainActivity de HackApp las cuales nos ayudaran a completar la accion que esta definida en el Intent,
si seleccionamos IntentReceiverActivity el flujo continuara normalmente hacia la Activity legitima de la aplicacion.

Por otro lado en caso de seleccionar MainActivity notaras que esta disponible para leer los datos del nombre de usuario y contraseña y mostrarlos en un dialogo y comprometerlos.

### Paso 5 - Parchear LoginApp

En los pasos siguientes modificaremos LoginApp para aplicar los parches de seguridad, la aplicacion HackApp no sera capaz de leer los datos del intent enviados por la aplicacion LoginApp incluso si la aplicacion LoginApp alimenta los datos del intent.
Los pasos para resolver este hueco de seguridad seran:

1. Ir a la aplicacion LoginApp y abrir LoginActivity
2. Ir al metodo submitInfo modificar el intent reemplazando el metodo de .SetAction por el metodo .SetClassName

El metodo .SetClassName toma dos parametros, uno el paquete actual y dos el paquete al cual queremos dirigirnos, aplicando este sencillo cambio a la aplicacion LoginApp ignorara los filtros del intent convirtiendolo de un intent implicito a uno explicito mismo que se dirigira a la Actividad legitima.











