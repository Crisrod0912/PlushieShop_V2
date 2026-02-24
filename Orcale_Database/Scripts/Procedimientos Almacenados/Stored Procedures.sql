CREATE OR REPLACE PROCEDURE ACTUALIZAR_FACTURA (
    fac_id NUMBER,
    tot NUMBER
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    UPDATE Factura
    SET total = tot, estado = 1
    WHERE id_factura = fac_id;
EXCEPTION    
    WHEN OTHERS THEN  
    VMES := SQLERRM;
    VCOD := SQLCODE;
    INSERT INTO ERRORES_AUDIT VALUES (USER, 'ACTUALIZAR_FACTURA', SYSDATE, VCOD || ' - '|| VMES);
END ACTUALIZAR_FACTURA;

CREATE OR REPLACE PROCEDURE INSERTAR_VENTA (
    fac_id NUMBER,
    prod_id NUMBER,
    prec NUMBER,
    cant NUMBER
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    INSERT INTO Venta (id_factura, id_producto, precio, cantidad)
    VALUES (fac_id, prod_id,prec, cant);
    UPDATE Producto
    SET existencias = existencias-cant
    WHERE id_producto = prod_id;
EXCEPTION    
    WHEN OTHERS THEN  
    VMES := SQLERRM;
    VCOD := SQLCODE;
    INSERT INTO ERRORES_AUDIT VALUES (USER, 'INSERTAR_VENTA', SYSDATE, VCOD || ' - '|| VMES);
END INSERTAR_VENTA;

CREATE OR REPLACE PROCEDURE CREAR_USUARIO (
    nomb VARCHAR2,
    apelli VARCHAR2,
    mail VARCHAR2,
    direc VARCHAR2,
    userna VARCHAR2,
    pass VARCHAR2,
    tarj VARCHAR2,
    pin_us VARCHAR2
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    INSERT INTO Usuario (nombre, apellido, correo, direccion, username, password, tarjeta, pin, fecha, activo, id_rol)
    VALUES (nomb, apelli, mail, direc, userna, pass, tarj, pin_us, SYSDATE, 0, 2);
EXCEPTION    
    WHEN OTHERS THEN  
    VMES := SQLERRM;
    VCOD := SQLCODE;
    INSERT INTO ERRORES_AUDIT VALUES (USER, 'CREAR_USUARIO', SYSDATE, VCOD || ' - '|| VMES);
END CREAR_USUARIO;

CREATE OR REPLACE PROCEDURE ACTUALIZAR_USUARIO (
    usuario_id NUMBER,
    nomb VARCHAR2,
    apelli VARCHAR2,
    mail VARCHAR2,
    direc VARCHAR2,
    userna VARCHAR2,
    pass VARCHAR2,
    tarj VARCHAR2,
    pin_us VARCHAR2
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    UPDATE Usuario
    SET nombre = nomb,
    apellido = apelli,
    correo = mail,
    direccion = direc,
    username = userna,
    password = pass,
    tarjeta = tarj,
    pin = pin_us,
    fecha = SYSDATE,
    activo = 1,
    id_rol = 2
WHERE id_usuario = usuario_id;
EXCEPTION    
    WHEN OTHERS THEN  
    VMES := SQLERRM;
    VCOD := SQLCODE;
    INSERT INTO ERRORES_AUDIT VALUES (USER, 'ACTUALIZAR_USUARIO', SYSDATE, VCOD || ' - '|| VMES);
END ACTUALIZAR_USUARIO;

CREATE OR REPLACE PROCEDURE GET_USU_USER_PASSCS (
    user_name VARCHAR2,
    pass VARCHAR2,
    CSDATOS OUT SYS_REFCURSOR
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    OPEN CSDATOS FOR SELECT id_usuario,nombre, apellido, correo, direccion, username, password, tarjeta, pin, fecha, activo, id_rol 
    FROM Usuario
    WHERE username = user_name AND password = pass;
EXCEPTION    
    WHEN OTHERS THEN  
    VMES := SQLERRM;
    VCOD := SQLCODE;
    INSERT INTO ERRORES_AUDIT VALUES (USER, 'GET_USU_USER_PASSCS', SYSDATE, VCOD || ' - '|| VMES);
END GET_USU_USER_PASSCS;

CREATE OR REPLACE PROCEDURE GET_USU_USER_CORRECS (
    user_name VARCHAR2,
    corre VARCHAR2,
    CSDATOS OUT SYS_REFCURSOR
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    OPEN CSDATOS FOR SELECT id_usuario,nombre, apellido, correo, direccion, username, password, tarjeta, pin, fecha, activo, id_rol 
    FROM Usuario
    WHERE username = user_name OR correo = corre;
EXCEPTION    
    WHEN OTHERS THEN  
    VMES := SQLERRM;
    VCOD := SQLCODE;
    INSERT INTO ERRORES_AUDIT VALUES (USER, 'GET_USU_USER_CORRECS', SYSDATE, VCOD || ' - '|| VMES);
END GET_USU_USER_CORRECS;

CREATE OR REPLACE PROCEDURE GET_USU_USERCS (
    user_name VARCHAR2,
    CSDATOS OUT SYS_REFCURSOR
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
    dinamico VARCHAR2(4000);
BEGIN
    dinamico := 'SELECT id_usuario, nombre, apellido, correo, direccion, username, password, tarjeta, pin, fecha, activo, id_rol FROM Usuario WHERE username = :user_name';
    OPEN CSDATOS FOR dinamico
    USING user_name;
EXCEPTION    
    WHEN OTHERS THEN  
    VMES := SQLERRM;
    VCOD := SQLCODE;
    INSERT INTO ERRORES_AUDIT VALUES (USER, 'GET_USU_USERCS', SYSDATE, VCOD || ' - '|| VMES);
END GET_USU_USERCS;

CREATE OR REPLACE PROCEDURE GET_PRODUCTOS (
    p_resultado OUT SYS_REFCURSOR
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN 
    OPEN p_resultado FOR
    SELECT * FROM producto;
EXCEPTION
    WHEN OTHERS THEN
        VMES := SQLERRM;
        VCOD := SQLCODE;
        INSERT INTO ERRORES_AUDIT (USUARIO, ORIGEN, FECHA, VERROR)
        VALUES (USER, 'GET_PRODUCTOS', SYSDATE, VCOD || ' - ' || VMES);
END GET_PRODUCTOS;

CREATE OR REPLACE PROCEDURE GET_PRODUCTO_BY_ID (
    p_id_producto IN NUMBER, 
    p_resultado OUT SYS_REFCURSOR
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    OPEN p_resultado FOR
    SELECT * FROM producto WHERE id_producto = p_id_producto;
EXCEPTION
    WHEN OTHERS THEN
        VMES := SQLERRM;
        VCOD := SQLCODE;
        INSERT INTO ERRORES_AUDIT (USUARIO, ORIGEN, FECHA, VERROR)
        VALUES (USER, 'GET_PRODUCTO_BY_ID', SYSDATE, VCOD || ' - ' || VMES);
END GET_PRODUCTO_BY_ID;

CREATE OR REPLACE PROCEDURE INSERT_PRODUCTO(
    p_id_producto IN NUMBER,
    p_id_categoria IN NUMBER,
    p_nombre IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_precio IN NUMBER,
    p_existencias IN NUMBER,
    p_ruta_imagen IN VARCHAR2,
    p_activo IN NUMBER
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN

    IF p_id_producto IS NULL THEN
        INSERT INTO producto (id_categoria, nombre, descripcion, precio, existencias, ruta_imagen, activo)
        VALUES (p_id_categoria, p_nombre, p_descripcion, p_precio, p_existencias, p_ruta_imagen, p_activo);
    ELSE
        UPDATE producto
        SET id_categoria = p_id_categoria,
            nombre = p_nombre,
            descripcion = p_descripcion,
            precio = p_precio,
            existencias = p_existencias,
            ruta_imagen = p_ruta_imagen,
            activo = p_activo
        WHERE id_producto = p_id_producto;
    END IF;
    
EXCEPTION
    WHEN OTHERS THEN
        VMES := SQLERRM;
        VCOD := SQLCODE;
        INSERT INTO ERRORES_AUDIT (USUARIO, ORIGEN, FECHA, VERROR)
        VALUES (USER, 'INSERT_PRODUCTO', SYSDATE, VCOD || ' - ' || VMES);
END INSERT_PRODUCTO;

CREATE OR REPLACE PROCEDURE DELETE_PRODUCTO (
    p_id_producto IN NUMBER
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    DELETE FROM producto WHERE id_producto = p_id_producto;
    
    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Producto no encontrado');
    END IF;
    
EXCEPTION
    WHEN OTHERS THEN
        VMES := SQLERRM;
        VCOD := SQLCODE;
        INSERT INTO ERRORES_AUDIT (USUARIO, ORIGEN, FECHA, VERROR)
        VALUES (USER, 'DELETE_PRODUCTO', SYSDATE, VCOD || ' - ' || VMES);
END DELETE_PRODUCTO;

CREATE OR REPLACE PROCEDURE FIND_PRODUCTO_BY_NOMBRE (
    p_nombre VARCHAR2, 
    p_resultado OUT SYS_REFCURSOR
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    OPEN p_resultado FOR
    SELECT *
    FROM producto
    WHERE REGEXP_LIKE(UPPER(nombre), UPPER(p_nombre));
EXCEPTION
    WHEN OTHERS THEN
        VMES := SQLERRM;
        VCOD := SQLCODE;
        INSERT INTO ERRORES_AUDIT (USUARIO, ORIGEN, FECHA, VERROR)
        VALUES (USER, 'FIND_PRODUCTO_BY_NOMBRE', SYSDATE, VCOD || ' - ' || VMES);
END FIND_PRODUCTO_BY_NOMBRE;

CREATE OR REPLACE PROCEDURE FIND_PRODUCTO_BY_CAT(
    cat_id NUMBER, 
    p_resultado OUT SYS_REFCURSOR
) AS
    VMES VARCHAR2(4000);
    VCOD NUMBER;
BEGIN
    OPEN p_resultado FOR
    SELECT *
    FROM producto
    WHERE id_categoria = cat_id;
EXCEPTION
    WHEN OTHERS THEN
        VMES := SQLERRM;
        VCOD := SQLCODE;
        INSERT INTO ERRORES_AUDIT (USUARIO, ORIGEN, FECHA, VERROR)
        VALUES (USER, 'FIND_PRODUCTO_BY_CAT', SYSDATE, VCOD || ' - ' || VMES);
END FIND_PRODUCTO_BY_CAT;

CREATE OR REPLACE PROCEDURE getFactura (
    p_Id_Factura INT
) IS
    v_fecha  FACTURA.fecha%TYPE;
    v_total  FACTURA.total%TYPE;
    v_estado FACTURA.estado%TYPE;
    v_mes     VARCHAR2(4000);
    v_cod     NUMBER;
BEGIN
    SELECT fecha, total, estado
    INTO v_fecha, v_total, v_estado
    FROM FACTURA
    WHERE Id_Factura = p_Id_Factura;
    
    DBMS_OUTPUT.PUT_LINE('=========================================');
    DBMS_OUTPUT.PUT_LINE('                FACTURA                  ');
    DBMS_OUTPUT.PUT_LINE('=========================================');
    DBMS_OUTPUT.PUT_LINE('Factura ID:       ' || p_Id_Factura);
    DBMS_OUTPUT.PUT_LINE('Fecha:            ' || TO_CHAR(v_fecha, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('-----------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Total:            $' || TO_CHAR(v_total, '999,999.99'));
    DBMS_OUTPUT.PUT_LINE('-----------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Estado:           ' || CASE v_estado
                                           WHEN 1 THEN 'Activo'
                                           ELSE 'Inactivo'
                                       END);
    DBMS_OUTPUT.PUT_LINE('=========================================');
    DBMS_OUTPUT.PUT_LINE('  ¡Gracias por su compra!                ');
    DBMS_OUTPUT.PUT_LINE('=========================================');
    
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se encontrÓ la factura con el ID ' || p_Id_Factura);
    WHEN OTHERS THEN
        v_mes := SQLERRM;
        v_cod := SQLCODE;
        INSERT INTO ERRORES_AUDIT VALUES (USER, 'EXISTE_USU',SYSDATE, v_cod || ' - '|| v_mes);
        DBMS_OUTPUT.PUT_LINE('Ocurri� un error: ' || v_mes);
END getFactura;
