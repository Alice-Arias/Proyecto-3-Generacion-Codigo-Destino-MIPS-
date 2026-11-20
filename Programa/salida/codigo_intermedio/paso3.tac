void$principal:
declaracionPrevia# int a
int1 = 10
a = int1
declaracionPrevia# int b
int2 = 3
b = int2
declaracionPrevia# bool activo
bool3 = true
activo = bool3
int4 = a
int5 = b
bool6 = int4 > int5
if bool6 goto principal$if$1$bloque1
goto principal$if$1$siguiente1

principal$if$1$bloque1:
int7 = 1
write int7
goto principal$if$1$final

principal$if$1$siguiente1:
int8 = 0
write int8

principal$if$1$final:
int9 = a
int10 = 10
bool11 = int9 == int10
int12 = b
int13 = 3
bool14 = int12 != int13
bool15 = bool11 and bool14
if bool15 goto principal$if$2$bloque1
goto principal$if$2$siguiente1

principal$if$2$bloque1:
int16 = 1
write int16
goto principal$if$2$final

principal$if$2$siguiente1:
int17 = 2
write int17

principal$if$2$final:
int18 = a
int19 = 5
bool20 = int18 < int19
if bool20 goto principal$if$3$bloque1
goto principal$if$3$siguiente1

principal$if$3$bloque1:
int21 = 1
write int21
goto principal$if$3$final

principal$if$3$siguiente1:
int22 = a
int23 = 10
bool24 = int22 <= int23
if bool24 goto principal$if$3$bloque2
goto principal$if$3$siguiente2

principal$if$3$bloque2:
int25 = 2
write int25
goto principal$if$3$final

principal$if$3$siguiente2:
int26 = 3
write int26

principal$if$3$final:
int27 = a
int28 = b
bool29 = int27 < int28
bool30 = not bool29
if bool30 goto principal$if$4$bloque1
goto principal$if$4$siguiente1

principal$if$4$bloque1:
int31 = 4
write int31

principal$if$4$siguiente1:

principal$if$4$final:
int32 = a
int33 = b
bool34 = int32 < int33
bool35 = activo
bool36 = bool34 or bool35
if bool36 goto principal$if$5$bloque1
goto principal$if$5$siguiente1

principal$if$5$bloque1:
int37 = 5
write int37

principal$if$5$siguiente1:

principal$if$5$final:
declaracionPrevia# int c
int38 = 3
c = int38

principal$while$6$condicion:
int39 = c
int40 = 0
bool41 = int39 > int40
if bool41 goto principal$while$6$bloque
goto principal$while$6$final

principal$while$6$bloque:
int42 = c
write int42
int43 = c
int44 = 1
int45 = int43 - int44
c = int45
goto principal$while$6$condicion

principal$while$6$final:

principal$for$7$inicializacion:
declaracionPrevia# int i
int46 = 0
i = int46

principal$for$7$condicion:
int47 = i
int48 = 10
bool49 = int47 < int48
if bool49 goto principal$for$7$bloque
goto principal$for$7$final

principal$for$7$incremento:
int50 = i
int51 = int50 + 1
i = int51
goto principal$for$7$condicion

principal$for$7$bloque:
int52 = i
int53 = 3
bool54 = int52 == int53
if bool54 goto principal$if$8$bloque1
goto principal$if$8$siguiente1

principal$if$8$bloque1:
goto principal$for$7$final

principal$if$8$siguiente1:

principal$if$8$final:
int55 = i
write int55
goto principal$for$7$incremento

principal$for$7$final:
return
void$principal$final
