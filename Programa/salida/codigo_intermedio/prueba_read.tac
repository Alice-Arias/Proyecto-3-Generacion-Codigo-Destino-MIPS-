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
string16 = "\nEdades seleccionadas: "
write string16

principal$for$1$inicializacion:
declaracionPrevia# int i
int17 = 0
i = int17

principal$for$1$condicion:
int18 = i
int19 = 5
bool20 = int18 < int19
if bool20 goto principal$for$1$bloque
goto principal$for$1$final

principal$for$1$incremento:
int21 = i
int22 = int21 + 1
i = int22
goto principal$for$1$condicion

principal$for$1$bloque:
int23 = edades[i]
write int23
goto principal$for$1$incremento

principal$for$1$final:
return
void$principal$final
