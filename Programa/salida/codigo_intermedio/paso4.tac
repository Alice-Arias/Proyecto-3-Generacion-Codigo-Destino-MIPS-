void$principal:
declaracionPrevia# float x
float1 = 7.5
x = float1
declaracionPrevia# float y
float2 = 2.0
y = float2
declaracion# float z
float3 = x
float4 = y
float5 = float3 + float4
z = float5
float6 = z
write float6
float7 = x
float8 = y
float9 = float7 * float8
z = float9
float10 = z
write float10
float11 = x
float12 = y
float13 = float11 / float12
z = float13
float14 = z
write float14
declaracionPrevia# string saludo
string15 = "Hola MIPS"
saludo = string15
string16 = saludo
write string16
string17 = "fin de textos"
write string17
float18 = x
float19 = y
bool20 = float18 > float19
if bool20 goto principal$if$1$bloque1
goto principal$if$1$siguiente1

principal$if$1$bloque1:
string21 = "x es mayor"
write string21

principal$if$1$siguiente1:

principal$if$1$final:
declaracionPrevia# char letra
char22 = 'A'
letra = char22
char23 = letra
char24 = 'A'
bool25 = char23 == char24
if bool25 goto principal$if$2$bloque1
goto principal$if$2$siguiente1

principal$if$2$bloque1:
string26 = "la letra es A"
write string26

principal$if$2$siguiente1:

principal$if$2$final:
return
void$principal$final
