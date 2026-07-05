<%@ page import="Singleton.*" %>
<%@ page import="Factory.*" %>
<%@ page import="Decorator.*" %>
<%@ page import="Composite.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="ClasesService.HotelService"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sistema de gestion de hotel</title>
        <style>
            .contenedorPrincipal{
                display: flex;
                flex-direction: row;
            }
            .contenedorBarraLateral{
                display: flex;
                background: #ff6633;
                height: 100vh;
                position: fixed;
                top:0;
                left:0;

                width: 15%;
            }
            .contenidoBarraLateral {
                display: flex;
                flex-direction: column;
                width: 100%;
                padding-top: 20px;
            }
            .contenidoBarraLateral button{
                height: 60px;
                background: #ff6633;
                border: none;
                font-size: 1em;
                text-align: left;
            }
            .contenedorBarraLateral button:hover{
                background: white;
                color: #ff6633;
            }
            .TextoHotel{
                width: 100%;
                text-align: center;
                font-size: 1.8em;
                font-family: Georgia;

            }
            .contenedorSecundario{
                display: flex;
                background: #ffff99;
                height: 100vh;
                position: fixed;
                top:0;
                right:0;
                width: 85%;
            }
            .contenidoBienvenida{
                display:flex;
                flex-direction: column;
                align-items: center;
                width: 100%;
            }
            .TextoBienvenida{
                width: 100%;
                text-align: center;
                font-size: 7em;
                font-family: Georgia;
                margin:0;
                margin-top: 110px;
            }
            .TextoMuestraHabitaciones{
                width: 100%;
                text-align: center;
                font-size: 7em;
                font-family: Georgia;
                margin:0;
                margin-top: 50px;
            }
            .textoBD{
                width: 100%;
                text-align: center;
                font-size: 1.5em;
                font-family: Georgia;
                margin: 0;
            }
            .cuadro {
                display: flex;
                flex-direction: column;
                align-items: center;
                width: auto;
                border-radius: 5%;
                padding: 13px 30px;
                border: none;
                box-shadow: 2px 2px 5px orange;
                background: #ffffcc;
                margin: 0;
                gap: 10px;
            }
            .ingresarButton{
                display:flex;
                margin:20px auto;
                align-self: center;
                font-size:1.2em;
                padding: 5px 20px;
            }
            .modal{
                display:none;
                position:fixed;
                top:0;
                left:0;
                width:100%;
                height:100%;
                background:rgba(0,0,0,0.5);
                justify-content:center;
                align-items:center;
                z-index:1000;
            }
            .modalActivo{
                display:flex;
            }
            .contenidoModal{
                position:relative; /* necesario para la X */
                display:flex;
                display: block ;
                flex-direction:column;
                gap:1px;
                justify-content: space-evenly;
                flex-wrap: wrap;
                background:#ccffcc;
                width:90%;
                max-width:450px;
                padding:30px;
                border-radius:12px;
                text-align:center;
                box-sizing:border-box;
                font-size: 1.5em;
            }
            .oculto{
                display:none !important;
            }
            .contenidoReserva{
                display:flex;
                flex-direction: column;
                align-items: center;
                width: 100%;
            }
            .radioPosicion{
                display: flex;
                flex-direction: row;
            }
            .contenidoMuestrarH{
                display:flex;
                flex-direction: column;
                align-items: center;
                width: 100%;
            }
            .contenedorPisos{
                display:flex;
                flex-direction: row;
                align-items: flex-start;
                justify-content: center;
                gap:90px;
            }
            .pisos{
                background-color: rgba(255, 255, 255, 0.5);
                border: 2px solid #333;
                border-radius: 10px;
                padding: 20px;
            }
            .pisoTitulo{
                font-size: 1.5em;
                font-weight: bold;
                margin-top: 0;
                margin-bottom: 15px;
                color: #111;
                border-bottom: 2px solid #333;
                padding-bottom: 5px;
            }
            .contenedorHabitaciones{
                display: grid;
                grid-template-columns: repeat(2, 120px);
                gap: 15px; /* Espaciado entre cada cuarto */
            }
            .tarjetaHabitacion {
                padding: 15px;
                border-radius: 6px;
                text-align: center;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                font-weight: bold;
            }
            /* Colores de fondo según el estado */
            .estadoDisponible {
                background-color: #28a745;
                color: white;
            }
            .estadoOcupado {
                background-color: #dc3545;
                color: white;
            }
            .estadoMantenimiento {
                background-color: #ffc107;
                color: #333;
            }
        </style>
        <script>
            function ingresarDatos() {
                document.getElementById("modalIngreso").classList.add("modalActivo");
            }
            function aceptar() {
                document.getElementById("modalIngreso").classList.remove("modalActivo");
                mostrarPisosHabitaciones();
            }
            function intentar() {
                document.getElementById("modalIngreso").classList.remove("modalActivo");
            }
            function reservarHabitacion() {
                document.getElementById("reserva").classList.remove("oculto");
                document.getElementById("bienvenida").classList.add("oculto");
                document.getElementById("muestraHabitaciones").classList.add("oculto");
            }
            function controlarPisosYCombos(checkSeleccionado) {
                var todosLosCheck = document.querySelectorAll('input[name="checkSeleccion"]');
                for (let pisoSelec of todosLosCheck) {
                    //conseguir el combobox q le pertenece al piso usando su valor(n° piso)
                    var comboAsociado = document.getElementById("comboHabitacion" + pisoSelec.value);
                    // Si el usuario MARCÓ el checkbox actual
                    if (checkSeleccionado.checked === true) {
                        //Si el piso se marco: muestra el combobox y se habilita
                        if (pisoSelec === checkSeleccionado) {
                            comboAsociado.style.display = "inline-block";
                            comboAsociado.disabled = false;
                            comboAsociado.required=true;
                        } else {
                            //si son los otros piso: bloquea su checkbox
                            pisoSelec.disabled = true;
                        }
                    } else {
                        //si el usuario desmarco: desbloquea todos los checkboxes y oculta todos los combos
                        pisoSelec.disabled = false;
                        comboAsociado.disabled = true;
                        comboAsociado.required=false;
                        comboAsociado.selectedIndex = 0;//resetea la seleccion del combox
                    }
                }
            }
            function mostrarPisosHabitaciones() {
                document.getElementById("muestraHabitaciones").classList.remove("oculto");
                document.getElementById("reserva").classList.add("oculto");
                document.getElementById("bienvenida").classList.add("oculto");
            }

            function validarPiso() {
                //se busca todos los checkboz de los pisoss
                var checkboxes = document.querySelectorAll('input[name="checkSeleccion"]');
                var algunoMarcado = false;
                //se recorre para ver si el usuario marco al menos uno
                for(var i=0; i<checkboxes.length; i++){
                    if (checkboxes[i].checked) {
                        algunoMarcado=true;
                        break;
                    }
                }
                if(!algunoMarcado){
                    alert("Error! Debes seleccionar un piso y elegir una habitacion para continuar con la reserva.")
                    return false; //frena el envio
                }
                return true;//deja pasar el formulario a java
            }
            
            function conAcompañantes(){
                var check = document.getElementById("si");
                var siAcompañante = document.getElementById("conAcompañante");
                if (check.checked) {
                    siAcompañante.classList.remove("oculto");
                    siAcompañante.required=true;
                }else{
                    siAcompañante.classList.add("oculto");
                    siAcompañante.required=false;
                }
            }
        </script>
    </head>
    <body>
        <%
            //Llamada a configuracion del nombre
            ConfiguracionService miConfig = ConfiguracionService.getInstancia();
            String nombre = miConfig.getNombreHotel();
            //Conexion de base de datos
            String estadoConexion ="";
            try{
            ConexionBD conexionBD = ConexionBD.getInstancia();
            Connection con = conexionBD.getConexion();
                if(con != null && !con.isClosed()){
                    estadoConexion="Base de datos conectada con exito!";
                }else{
                    estadoConexion="Error al conectar la base de datos";
            }
            }catch(Exception e){
                estadoConexion="Error al conectar: " + e.getMessage();
            }
            
            //validacion de seguridad
            String mensajeSeguridad="";
            //extrae los texto q el usuario escribio en los inputs
            String usuarioForm=request.getParameter("txtUsuario");
            String contraForm=request.getParameter("txtContraseña");
            boolean acceso=false;
            String claseModal="modal";
            //impide que valide datos inexistentes
            if(usuarioForm!=null &&contraForm!=null){
                acceso =SeguridadService.getInstancia().accesoPermitido(usuarioForm, contraForm);
                claseModal = "modal modalActivo";
                //si acceso=true
                if(acceso){
                    mensajeSeguridad = "<div id='accesoCorrecto'><div>Acceso Permitido</div><button type='button' onclick='aceptar()'><h3>Aceptar</h3></button></div>";
                //si acceso=false
                }else{
                    mensajeSeguridad = "<div id='accesoDenegado'><div>Acceso Denegado</div><button type='button' onclick='intentar()'><h3>Intentar de nuevo</h3></button></div>";
                }
            }
            //validar que haya dado click al boton de reservar
            if(request.getParameter("btnRegistrarDatos") != null){
                //extraer datos del huesped
            }
        %>
        <div clsss="contenedorPrincipal">
            <div class = "contenedorBarraLateral">
                <div class="contenidoBarraLateral">
                    <p class = "TextoHotel"><%= nombre%></p>
                    <button <%= !acceso ? "disabled" : "" %> onclick="reservarHabitacion()">&#x1F511 Reservar habitaci&#243n</button>
                    <button <%= !acceso ? "disabled" : "" %> onclick="mostrarPisosHabitaciones()">&#x1F3E2 Mostrar pisos con habitaciones</button>
                    <button <%= !acceso ? "disabled" : "" %>>&#x1F9F9 Agregar servicios</button>
                    <button <%= !acceso ? "disabled" : "" %>>&#x1F9C1 Mostrar huespedes</button>
                    <button <%= !acceso ? "disabled" : "" %>>&#x1F512 Pagar</button>
                </div>
            </div>
            <div class="contenedorSecundario">
                <div id="bienvenida" class="contenidoBienvenida">
                    <p class="TextoBienvenida">Bienvenido al <%=nombre%></p>
                    <p class="textoBD"><%= estadoConexion %></p>
                    <h1>-----Por seguridad-----</h1>
                    <!-- form para que salgan del navegador y lleguen a java
                    el action es para que envie los datos en esta misma pagina
                    el method POST:metodo de envio de los datos, en este caso
                    POST es para q se envie oculto y seguro y el usuario no 
                    vea nda-->
                    <form action="" method="POST">
                        <div class="cuadro">
                            <br><p class="textoBD">Ingresa tus datos para continuar</p><br>
                            <input class="usuario" type = "text" name="txtUsuario" placeholder="Usuario" required><br>
                            <input class="contraseña"type = "password" name="txtContraseña" placeholder="Contrase&#241a" required><br>
                        </div>
                        <button class = "ingresarButton" type="submit" onclick="ingresarDatos()"> Ingresar </button>
                        <div>
                            <p>Habitaciones disponibles: <%= HotelService.getInstancia().getCantidadHabitacionesDisponibles() %></p>
                        </div>
                        <div id = "modalIngreso" class ="<%= claseModal %>">
                            <div class="contenidoModal">
                                <div><%= mensajeSeguridad%></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="reserva" class ="contenidoReserva oculto">
                    <p class="TextoBienvenida">Reservar una habitacion</p>
                    <form action="" method="POST" onsubmit="return validarPiso()">
                        <div class="cuadro">
                            <br><p class="textoBD">Seleccionar habitacion por piso: </p><br>
                            <%
                            try{
                                ClasesService.HotelService hotel = ClasesService.HotelService.getInstancia();
                                //toda la estructura q estaba en la base de datos
                                java.util.List<Composite.Piso> pisosReserva = hotel.getListaPisos();
                                //recorre cada piso
                                for(Composite.Piso pisosR : pisosReserva){
                            %>
                            <div class="pisosConHabitacionesOption">
                                <!-- cada vuelta va a poner el numero del piso -->
                                <input type="checkbox" name="checkSeleccion" value="<%= pisosR.getNumero()%>" onclick="controlarPisosYCombos(this)">PISO: <%= pisosR.getNumero()%> ->
                                <!-- y a su lado el combobox -->
                                <select id="comboHabitacion<%= pisosR.getNumero()%>" name="cboHabitacion" disabled>
                                    <option value="">--Seleccione una habitación</option>
                                    <%
                                            //recorre las habitaciones por cada vuelta del for de pisos
                                            for(Factory.TipoHabitacion habitacionR : pisosR.getListaHabitaciones()){
                                                //si estan disponibles
                                                if(habitacionR.getEstado() instanceof State.Disponible){
                                    %>
                                    <!-- crea nuevas opciones-->
                                    <option value="<%= habitacionR.getNumero()%>">
                                        N°<%= habitacionR.getNumero()%> (<%= habitacionR.getClass().getSimpleName() %>)
                                    </option>
                                    <%
                                                }
                                            }   
                                    %>
                                </select>
                            </div>    

                            <%
                                }
                            }catch(Exception e) {
                            out.print("Error: " + e.getMessage());
                            }
                            %>
                            <br><p class="textoBD">Ingresar datos del huesped</p><br>
                            <input type="text" name="txtNombre" placeholder="Nombre completo" required>
                            <input type="text" name="txtDNI" placeholder="DNI" required>
                            <input type="text" name="txtEdad" placeholder="Edad" required>
                            <div class="radioPosicion" onchange="conAcompañantes()">
                                <input type="radio" name="opcion" id="si">Con acompañante(s)
                                
                                <input type="radio" name="opcion" id="no">Sin acompañante(s)
                            </div>
                            <input id="conAcompañante" type="text" class = "oculto" name="txtAcompañante" placeholder="DNI de los acompañantes">
                            <input type="text" name="txtProcedencia" placeholder="¿De donde viene?" required>
                            <input type="text" name="txtEstadoCivil" placeholder="Estado civil" required><br>
                            <button id="registrarDatos" class = "ingresarButton" type="submit" name="btnRegistrarDatos">Reservar habitacion</button>
                        </div>
                    </form>
                </div>
                <div id="muestraHabitaciones" class="contenidoMostrarH oculto">
                    <p class="TextoMuestraHabitaciones">Lista de pisos con habitaciones disponibles</p>
                    <div class="contenedorPisos">
                        <%
                        //para obtener los pisos con sus habitaciones
                        try {
                            //recupera la unica instancia activa(evita duplicar datos)
                            ClasesService.HotelService hotel = ClasesService.HotelService.getInstancia();
                            //llama a toda la estructura que se cargo en la base de datos
                            java.util.List<Composite.Piso> pisos = hotel.getListaPisos();
    
                            //recorrer cada piso
                            for(Composite.Piso piso : pisos) {
                        %>
                        <!-- en cada vuelta se genera un bloque nuevo de: -->
                        <div class="pisos">
                            <div class="pisoTitulo">
                                PISO <%= piso.getNumero()%>
                            </div>
                            <div class="contenedorHabitaciones">
                                <%
                                        //recorrer las habitaciones del piso
                                        for(Factory.TipoHabitacion h : piso.getListaHabitaciones()) {
                                            //determinar la clase de estilo css segun state
                                            String claseEstado = "estadoDisponible";//El estado es disponible por defecto
                                            String nombreEstado = "Disponible";
                                            //validar si el estado actual es igual a la clase mantenimiento
                                            if(h.getEstado() instanceof State.Mantenimiento) {
                                                //la clase cambia a:
                                                claseEstado="estadoMantenimiento";
                                                //el nombre cambia a:
                                                nombreEstado="Mantenimiento";
                                            //validar si el estado actual es igual a la clase ocupada
                                            } else if(h.getEstado() instanceof State.Ocupada) {
                                                claseEstado="estadoOcupado";
                                                nombreEstado="Ocupada";
                                            }
                                %>          <!-- dentro del for de habitaciones -->
                                <!-- cada vuelta se genera un bloque nuevo -->
                                <div class="tarjetaHabitacion <%= claseEstado %>">
                                    <div><br>N° <%= h.getNumero() %><br></div>
                                    <div><%= h.getClass().getSimpleName() %></div>
                                    <div><%= nombreEstado %></div>
                                </div>
                                <%
                                        } //fin bucle habitaciones
                                %>
                            </div>
                        </div>
                        <%
                            }//Fin bucle pisos
                        } catch(Exception e) {
                            out.print("Error: " + e.getMessage());
                        }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
