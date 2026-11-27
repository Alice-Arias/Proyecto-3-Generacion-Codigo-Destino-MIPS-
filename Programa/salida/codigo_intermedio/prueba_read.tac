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
string6 = "Ingrese la primera edad: "
write string6
read edad
int7 = edad
edades[0] = int7
string8 = "Ingrese la segunda edad: "
write string8
read edad
int9 = edad
edades[1] = int9
string10 = "Ingrese la tercera edad: "
write string10
read edad
int11 = edad
edades[2] = int11
string12 = "Ingrese la cuarta edad: "
write string12
read edad
int13 = edad
edades[3] = int13
string14 = "Ingrese la quinta edad: "
write string14
read edad
int15 = edad
edades[4] = int15
declaracionPrevia# int in1
int16 = 1
int17 = -14
int18 = int16 - int17
int19 = 5
int20 = 7
int21 = int19 + int20
int22 = 15
int23 = int21 // int22
int24 = 10
int25 = -3050 pot int24
int26 = int23 * int25
int27 = 3
int28 = int26 // int27
int29 = int18 mod int28
in1 = int29
int30 = in1
write int30
string31 = "\nEdades seleccionadas: "
write string31

principal$for$1$inicializacion:
declaracionPrevia# int i
int32 = 0
i = int32

principal$for$1$condicion:
int33 = i
int34 = 5
bool35 = int33 < int34
if bool35 goto principal$for$1$bloque
goto principal$for$1$final

principal$for$1$incremento:
int36 = i
int37 = int36 + 1
i = int37
goto principal$for$1$condicion

principal$for$1$bloque:
int38 = edades[i]
write int38
goto principal$for$1$incremento

principal$for$1$final:
return
void$principal$final
