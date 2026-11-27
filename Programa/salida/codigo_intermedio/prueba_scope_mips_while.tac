void$principal:
declaracionPrevia# int v1
int1 = 0
v1 = int1

principal$while$1$condicion:
int2 = v1
int3 = 2
bool4 = int2 < int3
if bool4 goto principal$while$1$bloque
goto principal$while$1$final

principal$while$1$bloque:
int5 = v1
int6 = 0
bool7 = int5 == int6
if bool7 goto principal$if$2$bloque1
goto principal$if$2$siguiente1

principal$if$2$bloque1:
declaracionPrevia# int v2
int8 = 10
v2 = int8
int9 = v2
write int9
goto principal$if$2$final

principal$if$2$siguiente1:
declaracionPrevia# float v3
float10 = 2.5
v3 = float10
float11 = v3
write float11

principal$if$2$final:
int12 = v1
int13 = 1
int14 = int12 + int13
v1 = int14
goto principal$while$1$condicion

principal$while$1$final:
return
void$principal$final
