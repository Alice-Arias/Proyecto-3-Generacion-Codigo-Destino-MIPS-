declaracionPrevia# int contador
int1 = 0
contador = int1
declaracionPrevia# float pi
float2 = 3.5
pi = float2
goto void$principal

int$factorial:
param_int n
int3 = n
int4 = 1
bool5 = int3 <= int4
if bool5 goto factorial$if$1$bloque1
goto factorial$if$1$siguiente1

factorial$if$1$bloque1:
int6 = 1
return int6

factorial$if$1$siguiente1:

factorial$if$1$final:
int7 = n
int8 = n
int9 = 1
int10 = int8 - int9
param int10
int11 = call int$factorial, 1
int12 = int7 * int11
return int12
int$factorial$final

int$fib:
param_int n
int13 = n
int14 = 2
bool15 = int13 < int14
if bool15 goto fib$if$2$bloque1
goto fib$if$2$siguiente1

fib$if$2$bloque1:
int16 = n
return int16

fib$if$2$siguiente1:

fib$if$2$final:
int17 = n
int18 = 1
int19 = int17 - int18
param int19
int20 = call int$fib, 1
int21 = n
int22 = 2
int23 = int21 - int22
param int23
int24 = call int$fib, 1
int25 = int20 + int24
return int25
int$fib$final

int$suma:
param_int a
param_int b
declaracionPrevia# int r
int26 = a
int27 = b
int28 = int26 + int27
r = int28
int29 = r
return int29
int$suma$final

float$area:
param_float r
float30 = pi
float31 = r
float32 = float30 * float31
float33 = r
float34 = float32 * float33
return float34
float$area$final

char$inicial:
param_char c
param_bool mayus
bool35 = mayus
if bool35 goto inicial$if$3$bloque1
goto inicial$if$3$siguiente1

inicial$if$3$bloque1:
char36 = 'A'
return char36

inicial$if$3$siguiente1:

inicial$if$3$final:
char37 = c
return char37
char$inicial$final

int$contar:
int38 = contador
int39 = 1
int40 = int38 + int39
contador = int40
int41 = contador
return int41
int$contar$final

int$diez:
int42 = 10
return int42
int$diez$final

void$principal:
int43 = 5
param int43
int44 = call int$factorial, 1
write int44
int45 = 10
param int45
int46 = call int$fib, 1
write int46
int47 = 3
int48 = 4
param int47
param int48
int49 = call int$suma, 2
write int49
int50 = 1
int51 = 2
param int50
param int51
int52 = call int$suma, 2
int53 = call int$diez, 0
param int52
param int53
int54 = call int$suma, 2
write int54
float55 = 2.0
param float55
float56 = call float$area, 1
write float56
declaracionPrevia# char letra
char57 = 'z'
bool58 = false
param char57
param bool58
char59 = call char$inicial, 2
letra = char59
char60 = letra
char61 = 'z'
bool62 = char60 == char61
if bool62 goto principal$if$4$bloque1
goto principal$if$4$siguiente1

principal$if$4$bloque1:
string63 = "inicial correcta"
write string63

principal$if$4$siguiente1:

principal$if$4$final:
char64 = 'z'
bool65 = true
param char64
param bool65
char66 = call char$inicial, 2
letra = char66
char67 = letra
char68 = 'A'
bool69 = char67 == char68
if bool69 goto principal$if$5$bloque1
goto principal$if$5$siguiente1

principal$if$5$bloque1:
string70 = "mayuscula correcta"
write string70

principal$if$5$siguiente1:

principal$if$5$final:
int71 = call int$contar, 0
int72 = call int$contar, 0
int73 = call int$contar, 0
int74 = contador
write int74
return
void$principal$final
