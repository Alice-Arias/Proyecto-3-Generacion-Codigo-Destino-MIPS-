void$principal:
declaracion# int edad
declaracionPrevia# int edades[5]
int1 = 0
int2 = 0
int3 = 0
int4 = 0
int5 = 0
edades[0] = int1
edades[1] = int2
edades[2] = int3
edades[3] = int4
edades[4] = int5
declaracionPrevia# int i
int6 = 1
i = int6
declaracionPrevia# int a
int7 = 5
a = int7
declaracionPrevia# int b
int8 = 2
b = int8
string9 = "Ingrese la primera edad: "
write string9
read edad
int10 = edad
edades[0] = int10
string11 = "Ingrese la segunda edad: "
write string11
read edad
int12 = edad
edades[1] = int12
string13 = "Ingrese la tercera edad: "
write string13
read edad
int14 = edad
edades[2] = int14
string15 = "Ingrese la cuarta edad: "
write string15
read edad
int16 = edad
edades[3] = int16
string17 = "Ingrese la quinta edad: "
write string17
read edad
int18 = edad
edades[4] = int18
declaracionPrevia# int in1
int19 = i
int20 = int19 + 1
i = int20
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
in1 = int33
int34 = in1
write int34
int35 = a
int36 = b
bool37 = int35 < int36
bool38 = not bool37
if bool38 goto principal$if$1$bloque1
goto principal$if$1$siguiente1

principal$if$1$bloque1:
string39 = "Funciona el not"
write string39

principal$if$1$siguiente1:

principal$if$1$final:
string40 = "\nEdades seleccionadas: "
write string40

principal$for$2$inicializacion:
declaracionPrevia# int y
int41 = 0
y = int41

principal$for$2$condicion:
int42 = y
int43 = 5
bool44 = int42 < int43
if bool44 goto principal$for$2$bloque
goto principal$for$2$final

principal$for$2$incremento:
int45 = y
int46 = int45 + 1
y = int46
goto principal$for$2$condicion

principal$for$2$bloque:
int47 = edades[y]
write int47
goto principal$for$2$incremento

principal$for$2$final:
return
void$principal$final
