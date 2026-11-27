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
string7 = "Ingrese la primera edad: "
write string7
read edad
int8 = edad
edades[0] = int8
string9 = "Ingrese la segunda edad: "
write string9
read edad
int10 = edad
edades[1] = int10
string11 = "Ingrese la tercera edad: "
write string11
read edad
int12 = edad
edades[2] = int12
string13 = "Ingrese la cuarta edad: "
write string13
read edad
int14 = edad
edades[3] = int14
string15 = "Ingrese la quinta edad: "
write string15
read edad
int16 = edad
edades[4] = int16
declaracionPrevia# int in1
int17 = i
int18 = int17 + 1
i = int18
int19 = -14
int20 = int17 - int19
int21 = 5
int22 = 7
int23 = int21 + int22
int24 = 15
int25 = int23 // int24
int26 = 10
int27 = -3050 pot int26
int28 = int25 * int27
int29 = 3
int30 = int28 // int29
int31 = int20 mod int30
in1 = int31
int32 = in1
write int32
string33 = "\nEdades seleccionadas: "
write string33

principal$for$1$inicializacion:
declaracionPrevia# int y
int34 = 0
y = int34

principal$for$1$condicion:
int35 = y
int36 = 5
bool37 = int35 < int36
if bool37 goto principal$for$1$bloque
goto principal$for$1$final

principal$for$1$incremento:
int38 = y
int39 = int38 + 1
y = int39
goto principal$for$1$condicion

principal$for$1$bloque:
int40 = edades[y]
write int40
goto principal$for$1$incremento

principal$for$1$final:
return
void$principal$final
