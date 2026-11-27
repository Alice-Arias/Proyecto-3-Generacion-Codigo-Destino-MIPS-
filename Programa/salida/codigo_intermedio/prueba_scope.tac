declaracion# int numero
declaracion# int g
declaracion# float ff
goto void$principal

float$func1:
param_char x22
param_char x23
declaracionPrevia# string sss
string1 = "Hola"
sss = string1
int2 = 20
int3 = 21
int4 = int2 + int3
numero = int4
declaracionPrevia# char letra
char5 = 'a'
letra = char5
declaracion# float h
int6 = numero
int7 = int6 + 1
numero = int7
int8 = 10
int9 = 24
int10 = int8 + int9
int11 = int6 * int10
int12 = 44
int13 = 88
int14 = int12 pot int13
int15 = int11 // int14
int16 = numero
int17 = int16 - 1
numero = int17
int18 = int15 mod int16
g = int18
float19 = 4.4
char20 = letra
char21 = 'b'
param char20
param char21
float22 = call float$func1, 2
float23 = float19 * float22
float24 = 9.9
float25 = 6.7
float26 = float24 - float25
float27 = float23 / float26
h = float27
int28 = numero
write int28
float29 = -0.01
ff = float29
declaracionPrevia# char _miChar_
char30 = '!'
_miChar_ = char30
declaracionPrevia# int _x30_
int31 = -1
_x30_ = int31
declaracionPrevia# bool _x40_
bool32 = false
_x40_ = bool32
declaracionPrevia# int _x50_[2]
int33 = 4
int34 = 5
_x50_[0] = int33
_x50_[1] = int34
int35 = 1
int36 = 0
int37 = int35 + int36
int38 = -9999
_x50_[int37] = int38
declaracionPrevia# float _x502_[4]
float39 = 5.5
float40 = 6.4
float41 = 7.7
float42 = 8.8
_x502_[0] = float39
_x502_[1] = float40
_x502_[2] = float41
_x502_[3] = float42
string43 = "1****"
write string43
int44 = _x30_
int45 = 2
int46 = int44 - int45
float47 = _x502_[int46]
char48 = letra
char49 = 'c'
param char48
param char49
float50 = call float$func1, 2
float51 = float47 * float50
ff = float51
declaracionPrevia# string _x503_
string52 = "Hola a todos los que estʃ:a:ʅ haciendo un compilador nuevo¿:n"
_x503_ = string52
string53 = _x503_
write string53
int54 = numero
int55 = 45
bool56 = int54 <= int55
float57 = ff
float58 = 5.6
bool59 = float57 > float58
bool60 = bool56 and bool59
if bool60 goto func1$if$1$bloque1
goto func1$if$1$siguiente1

func1$if$1$bloque1:
declaracion# int y1
string61 = "2****"
write string61
float62 = ff
float63 = 5.6
bool64 = float62 > float63
int65 = numero
int66 = 45
bool67 = int65 > int66
bool68 = bool64 or bool67
bool69 = not bool68
if bool69 goto func1$if$2$bloque1
goto func1$if$2$siguiente1

func1$if$2$bloque1:
declaracion# int y
declaracionPrevia# char ch33
char70 = 'a'
ch33 = char70
string71 = "22****22"
write string71

func1$if$2$siguiente1:

func1$if$2$final:
string72 = "2****"
write string72
declaracionPrevia# char ch33
char73 = 'a'
ch33 = char73
goto func1$if$1$final

func1$if$1$siguiente1:
float74 = 44.55
float75 = ff
bool76 = float74 < float75
if bool76 goto func1$if$1$bloque2
goto func1$if$1$siguiente2

func1$if$1$bloque2:
string77 = "3****"
write string77
declaracionPrevia# int y2
int78 = 45
y2 = int78
declaracionPrevia# string str2
string79 = "dddfffddd"
str2 = string79

func1$while$3$condicion:
bool80 = false
bool81 = _x40_
bool82 = bool80 and bool81
if bool82 goto func1$while$3$bloque
goto func1$while$3$final

func1$while$3$bloque:
string83 = _x503_
write string83
string84 = "4!!!!!!!!!!!!!!!!!"
write string84

func1$for$4$inicializacion:
declaracionPrevia# int numSS
int85 = 0
numSS = int85

func1$for$4$condicion:
int86 = numSS
int87 = 7
bool88 = int86 < int87
if bool88 goto func1$for$4$bloque
goto func1$for$4$final

func1$for$4$incremento:
int89 = numSS
int90 = int89 + 1
numSS = int90
goto func1$for$4$condicion

func1$for$4$bloque:
int91 = numSS
write int91
goto func1$for$4$incremento

func1$for$4$final:
string92 = "4!!!!!!!!!!!!!!!!!"
write string92
goto func1$while$3$condicion

func1$while$3$final:
string93 = "3****"
write string93
goto func1$if$1$final

func1$if$1$siguiente2:
string94 = "4****"
write string94

func1$if$1$final:
float95 = 444.555
float96 = ff
bool97 = float95 < float96
if bool97 goto func1$if$5$bloque1
goto func1$if$5$siguiente1

func1$if$5$bloque1:
string98 = "5****"
write string98
goto func1$if$5$final

func1$if$5$siguiente1:
string99 = "6****"
write string99

func1$if$5$final:
string100 = "Hola mundo"
write string100
float101 = -5.6
float102 = ff
float103 = float101 / float102
char104 = letra
char105 = 'd'
param char104
param char105
float106 = call float$func1, 2
float107 = float103 * float106
return float107
float$func1$final

int$_func2_:
param_float _b1_
param_int _i1_
declaracion# int j
int108 = _i1_
return int108
int$_func2_$final

bool$_func3_:
declaracion# string _b1_
bool109 = true
return bool109
bool$_func3_$final

void$principal:
declaracionPrevia# char miChar
char110 = '!'
miChar = char110
declaracionPrevia# string str1
string111 = "Mi string 1"
str1 = string111
declaracionPrevia# float fl1
float112 = 56.6
fl1 = float112
declaracionPrevia# int in1
int113 = 10
in1 = int113
declaracionPrevia# bool bl0
float114 = 6.7
float115 = 8.9
bool116 = float114 != float115
bl0 = bool116
declaracionPrevia# int _x50_[2]
int117 = 4
int118 = 5
_x50_[0] = int117
_x50_[1] = int118
int119 = 0
char120 = miChar
char121 = 'e'
param char120
param char121
float122 = call float$func1, 2
float123 = 5.5
float124 = float122 * float123
int125 = in1
int126 = _x50_[0]
int127 = int125 // int126
param float124
param int127
int128 = call int$_func2_, 2
int129 = int119 + int128
int130 = 0
int131 = int129 + int130
in1 = int131
return
void$principal$final
