CREATE OR REPLACE PACKAGE PKG_VENTAS AS
    FUNCTION CREAR_FACTURA (
        usu_id NUMBER
    ) RETURN NUMBER;
    FUNCTION GET_VENTAS RETURN SYS_REFCURSOR;
    PROCEDURE INSERTAR_VENTA (
        fac_id NUMBER, 
        prod_id NUMBER, 
        prec NUMBER, 
        cant NUMBER
    );
    PROCEDURE ACTUALIZAR_FACTURA (
        fac_id NUMBER, 
        tot NUMBER
    );
    PROCEDURE getFactura (
        p_Id_Factura INT
    );
END PKG_VENTAS;

CREATE OR REPLACE PACKAGE PKG_USER AS
    FUNCTION EXISTE_USU (
        user_name VARCHAR2,
        correo_user VARCHAR2
    ) RETURN NUMBER;
    FUNCTION GET_USU_USER (
        user_name VARCHAR2
    ) RETURN SYS_REFCURSOR;
    FUNCTION GET_USU_USER_CORRE (
        user_name VARCHAR2,
        corre VARCHAR2
    ) RETURN SYS_REFCURSOR;
    FUNCTION GET_USU_USER_PASS (
        user_name VARCHAR2,
        pass VARCHAR2
    ) RETURN SYS_REFCURSOR;
    PROCEDURE ACTUALIZAR_USUARIO (
        usuario_id NUMBER,
        nomb VARCHAR2,
        apelli VARCHAR2,
        mail VARCHAR2,
        direc VARCHAR2,
        userna VARCHAR2,
        pass VARCHAR2,
        tarj VARCHAR2,
        pin_us VARCHAR2
    );
    PROCEDURE CREAR_USUARIO (
        nomb VARCHAR2,
        apelli VARCHAR2,
        mail VARCHAR2,
        direc VARCHAR2,
        userna VARCHAR2,
        pass VARCHAR2,
        tarj VARCHAR2,
        pin_us VARCHAR2
    );
    PROCEDURE GET_USU_USER_CORRECS (
        user_name VARCHAR2,
        corre VARCHAR2,
        CSDATOS OUT SYS_REFCURSOR
    );
    PROCEDURE GET_USU_USER_PASSCS (
        user_name VARCHAR2,
        pass VARCHAR2,
        CSDATOS OUT SYS_REFCURSOR
    );
    PROCEDURE GET_USU_USERCS (
        user_name VARCHAR2,
        CSDATOS OUT SYS_REFCURSOR
    );
END PKG_USER;

CREATE OR REPLACE PACKAGE PKG_PRODUCTO AS
    FUNCTION GET_VENTAS RETURN SYS_REFCURSOR;
    PROCEDURE DELETE_PRODUCTO (
        p_id_producto IN NUMBER
    );
    PROCEDURE FIND_PRODUCTO_BY_NOMBRE (
        p_nombre VARCHAR2, 
        p_resultado OUT SYS_REFCURSOR
    );
    PROCEDURE GET_PRODUCTO_BY_ID (
        p_id_producto IN NUMBER, 
        p_resultado OUT SYS_REFCURSOR
    );
    PROCEDURE GET_PRODUCTOS (
        p_resultado OUT SYS_REFCURSOR
    );
    PROCEDURE INSERT_PRODUCTO (
        p_id_producto IN NUMBER,
        p_id_categoria IN NUMBER,
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_precio IN NUMBER,
        p_existencias IN NUMBER,
        p_ruta_imagen IN VARCHAR2,
        p_activo IN NUMBER
    );
END PKG_PRODUCTO;

CREATE OR REPLACE PACKAGE PKG_CATEGORIA AS
    FUNCTION GET_VENTAS RETURN SYS_REFCURSOR;
    PROCEDURE GET_CATEGORIA_BY_ID (
        p_id_categoria IN NUMBER, 
        p_resultado OUT SYS_REFCURSOR
    );
    PROCEDURE GET_CATEGORIAS (
        p_resultado OUT SYS_REFCURSOR
    );
END PKG_CATEGORIA;
