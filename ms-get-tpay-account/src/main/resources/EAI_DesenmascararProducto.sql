CREATE TABLE dbo.Cuentas (
    Cuenta VARCHAR(16),   -- Número de cuenta o producto desenmascarado
    Tipo VARCHAR(3)       -- Tipo de producto (DDA para Cuenta Corriente, SAV para Cuenta de Ahorros)
);


INSERT INTO dbo.Cuentas(Cuenta, Tipo) VALUES ('07908742626', 'SAV');
INSERT INTO dbo.Cuentas(Cuenta, Tipo) VALUES ('03289578563', 'DDA');
INSERT INTO dbo.Cuentas(Cuenta, Tipo) VALUES ('09876543218', 'DDA');
INSERT INTO dbo.Cuentas(Cuenta, Tipo) VALUES ('22334455663', 'SAV');


CREATE PROCEDURE [dbo].[EAI_DesenmascararProducto](
    @ProductoEnmascarado VARCHAR(16),
    @TipoProducto VARCHAR(16),
    @ProductoDesenmascarado VARCHAR(16) OUTPUT,
    @Resultado VARCHAR(3) OUTPUT
)
AS
BEGIN
    SET NOCOUNT ON;

    -- Desenmascarar el producto según el tipo
    IF @TipoProducto = 'CuentaCorriente'
    BEGIN
        SET @ProductoDesenmascarado = REPLACE(@ProductoEnmascarado, 'CC_', '');
    END
    ELSE IF @TipoProducto = 'CuentaAhorros'
    BEGIN
        SET @ProductoDesenmascarado = REPLACE(@ProductoEnmascarado, 'SAV_', '');
    END
    ELSE
    BEGIN
        SET @ProductoDesenmascarado = NULL;
    END

    -- Si no se pudo desenmascarar el producto
    IF @ProductoDesenmascarado IS NULL
    BEGIN
        SET @Resultado = 'T10'; -- Producto enmascarado no encontrado.
    END
    ELSE
    BEGIN
        -- Asignar el tipo de producto
        DECLARE @Tipo VARCHAR(3);
        SET @Tipo = CASE(@TipoProducto) WHEN 'CuentaCorriente' THEN 'DDA' ELSE 'SAV' END;

        -- Verificar si el producto desenmascarado existe en la tabla Cuentas
        IF EXISTS(SELECT * FROM dbo.Cuentas WHERE Cuenta LIKE '%' + @ProductoDesenmascarado AND Tipo = @Tipo)
        BEGIN
            IF LEN(@ProductoEnmascarado) <= 10
            BEGIN
                -- Si el producto es corto, agregamos el tipo al principio del número de cuenta
                SET @ProductoDesenmascarado = (SELECT TOP(1) Tipo FROM dbo.Cuentas WHERE Cuenta = @ProductoDesenmascarado) + @ProductoDesenmascarado;
            END
            ELSE
            BEGIN
                -- Si el producto es largo, agregamos el tipo al final del número de cuenta
                SET @ProductoDesenmascarado = (SELECT TOP(1) Tipo FROM dbo.Cuentas WHERE Cuenta LIKE '%' + @ProductoDesenmascarado) + @ProductoDesenmascarado;
            END
            SET @Resultado = '000'; -- Éxito
        END
        ELSE
        BEGIN
            SET @Resultado = 'T10'; -- Tipo de producto desenmascarado no encontrado o no coincide
        END
    END
END;
