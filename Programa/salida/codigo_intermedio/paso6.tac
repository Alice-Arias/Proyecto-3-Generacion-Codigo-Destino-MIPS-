declaracionPrevia# int numeros[5]
int1 = 10
int2 = 20
int3 = 30
int4 = 40
int5 = 50
numeros[0] = int1
numeros[1] = int2
numeros[2] = int3
numeros[3] = int4
numeros[4] = int5
declaracionPrevia# float notas[3]
float6 = 9.5
float7 = 8.0
float8 = 7.25
notas[0] = float6
notas[1] = float7
notas[2] = float8
declaracion# int copia[5]
goto void$principal

int$sumarHasta:
param_int tope
declaracionPrevia# int datos[4]
int9 = 1
int10 = 2
int11 = 3
int12 = 4
datos[0] = int9
datos[1] = int10
datos[2] = int11
datos[3] = int12
declaracionPrevia# int s
int13 = 0
s = int13

sumarHasta$for$1$inicializacion:
declaracionPrevia# int i
int14 = 0
i = int14

sumarHasta$for$1$condicion:
int15 = i
int16 = tope
bool17 = int15 < int16
if bool17 goto sumarHasta$for$1$bloque
goto sumarHasta$for$1$final

sumarHasta$for$1$incremento:
int18 = i
int19 = int18 + 1
i = int19
goto sumarHasta$for$1$condicion

sumarHasta$for$1$bloque:
int20 = s
int21 = datos[i]
int22 = int20 + int21
s = int22
goto sumarHasta$for$1$incremento

sumarHasta$for$1$final:
int23 = s
return int23
int$sumarHasta$final

int$doble:
param_int x
int24 = x
int25 = 2
int26 = int24 * int25
return int26
int$doble$final

void$principal:
int27 = numeros[0]
write int27
int28 = numeros[4]
write int28
int29 = 99
numeros[2] = int29
int30 = numeros[2]
write int30
declaracionPrevia# int k
int31 = 1
k = int31
int32 = numeros[k]
write int32
int33 = k
int34 = 2
int35 = int33 + int34
int36 = numeros[k]
int37 = 5
int38 = int36 + int37
copia[int35] = int38
int39 = copia[3]
write int39
declaracionPrevia# int total
int40 = 0
total = int40

principal$for$2$inicializacion:
declaracionPrevia# int i
int41 = 0
i = int41

principal$for$2$condicion:
int42 = i
int43 = 5
bool44 = int42 < int43
if bool44 goto principal$for$2$bloque
goto principal$for$2$final

principal$for$2$incremento:
int45 = i
int46 = int45 + 1
i = int46
goto principal$for$2$condicion

principal$for$2$bloque:
int47 = total
int48 = numeros[i]
int49 = int47 + int48
total = int49
goto principal$for$2$incremento

principal$for$2$final:
int50 = total
write int50
float51 = notas[2]
write float51
int52 = 4
param int52
int53 = call int$sumarHasta, 1
write int53
int54 = numeros[4]
param int54
int55 = call int$doble, 1
write int55
return
void$principal$final
