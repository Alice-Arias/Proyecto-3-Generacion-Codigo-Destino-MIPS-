void$principal:
declaracion# int v1
declaracionPrevia# int v2[5]
int1 = 0
int2 = 0
int3 = 0
int4 = 0
int5 = 0
v2[0] = int1
v2[1] = int2
v2[2] = int3
v2[3] = int4
v2[4] = int5
declaracionPrevia# int v3
int6 = 1
v3 = int6
declaracionPrevia# int v4
int7 = 5
v4 = int7
declaracionPrevia# int v5
int8 = 2
v5 = int8
string9 = "Ingrese la primera edad: "
write string9
read v1
int10 = v1
v2[0] = int10
string11 = "Ingrese la segunda edad: "
write string11
read v1
int12 = v1
v2[1] = int12
string13 = "Ingrese la tercera edad: "
write string13
read v1
int14 = v1
v2[2] = int14
string15 = "Ingrese la cuarta edad: "
write string15
read v1
int16 = v1
v2[3] = int16
string17 = "Ingrese la quinta edad: "
write string17
read v1
int18 = v1
v2[4] = int18
declaracionPrevia# int v6
int19 = v3
int20 = int19 + 1
v3 = int20
int21 = -14
int22 = int19 - int21
int23 = 5
int24 = 7
int25 = int23 + int24
int26 = 15
int27 = int25 // int26
int28 = 10
int29 = -3050 pot int28
int30 = int27 * int29
int31 = 3
int32 = int30 // int31
int33 = int22 mod int32
v6 = int33
int34 = v6
write int34
int35 = v4
int36 = v5
bool37 = int35 < int36
bool38 = not bool37
if bool38 goto principal$if$1$bloque1
goto principal$if$1$siguiente1

principal$if$1$bloque1:
string39 = "Funciona el not"
write string39

principal$if$1$siguiente1:

principal$if$1$final:
declaracionPrevia# int v7
int40 = -142
int41 = 52
int42 = int40 mod int41
v7 = int42
string43 = "\nEdades seleccionadas: "
write string43

principal$for$2$inicializacion:
declaracionPrevia# int v8
int44 = 0
v8 = int44

principal$for$2$condicion:
int45 = v8
int46 = 5
bool47 = int45 < int46
if bool47 goto principal$for$2$bloque
goto principal$for$2$final

principal$for$2$incremento:
int48 = v8
int49 = int48 + 1
v8 = int49
goto principal$for$2$condicion

principal$for$2$bloque:
int50 = v2[v8]
write int50
goto principal$for$2$incremento

principal$for$2$final:
return
void$principal$final
