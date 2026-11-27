.data

# ---------- variables del programa ----------
var_letrica: .word 0
.align 2
var__arr_: .space 80
var__i_: .word 0
.align 2
var_arr: .space 12
var_pi: .float 0.0
.align 2
var_notas: .space 12
var_activo: .word 0
var_saludo: .word 0

# ---------- temporales del codigo intermedio ----------
tmp_char1: .word 0
tmp_int2: .word 0
tmp_int3: .word 0
tmp_int4: .word 0
tmp_int5: .word 0
tmp_float6: .float 0.0
tmp_float7: .float 0.0
tmp_float8: .float 0.0
tmp_float9: .float 0.0
tmp_bool10: .word 0
tmp_string11: .word 0

# ---------- numeros float fijos ----------
num_1: .float 3.14
num_2: .float 9.5
num_3: .float 8.0
num_4: .float 7.25
num_5: .float 2.0
num_6: .float 7.5

# ---------- textos fijos ----------
txt_1: .asciiz "hola"
txt_2: .asciiz "sdff"
txt_3: .asciiz "Hola mundo"
txt_4: .asciiz "tres"
txt_5: .asciiz "dos"
txt_6: .asciiz "uno"
txt_7: .asciiz "no es A"

.text
.globl main
main:
    li $t0, 53                      # char1 = '5'
    sw $t0, tmp_char1
    lw $t0, tmp_char1               # letrica = char1
    sw $t0, var_letrica
    li $t0, 5                       # int2 = 5
    sw $t0, tmp_int2
    lw $t0, tmp_int2                # _i_ = int2
    sw $t0, var__i_
    li $t0, 22                      # int3 = 22
    sw $t0, tmp_int3
    li $t0, 10                      # int4 = 10
    sw $t0, tmp_int4
    li $t0, 11                      # int5 = 11
    sw $t0, tmp_int5
    lw $t0, tmp_int3                # arr[0] = int3
    la $t9, var_arr
    sw $t0, 0($t9)
    lw $t0, tmp_int4                # arr[1] = int4
    la $t9, var_arr
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    lw $t0, tmp_int5                # arr[2] = int5
    la $t9, var_arr
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    l.s $f0, num_1                  # float6 = 3.14
    s.s $f0, tmp_float6
    l.s $f0, tmp_float6             # pi = float6
    s.s $f0, var_pi
    l.s $f0, num_2                  # float7 = 9.5
    s.s $f0, tmp_float7
    l.s $f0, num_3                  # float8 = 8.0
    s.s $f0, tmp_float8
    l.s $f0, num_4                  # float9 = 7.25
    s.s $f0, tmp_float9
    l.s $f0, tmp_float7             # notas[0] = float7
    la $t9, var_notas
    s.s $f0, 0($t9)
    l.s $f0, tmp_float8             # notas[1] = float8
    la $t9, var_notas
    addiu $t9, $t9, 4
    s.s $f0, 0($t9)
    l.s $f0, tmp_float9             # notas[2] = float9
    la $t9, var_notas
    addiu $t9, $t9, 8
    s.s $f0, 0($t9)
    li $t0, 1                       # bool10 = true
    sw $t0, tmp_bool10
    lw $t0, tmp_bool10              # activo = bool10
    sw $t0, var_activo
    la $t0, txt_1                   # string11 = "hola"
    sw $t0, tmp_string11
    lw $t0, tmp_string11            # saludo = string11
    sw $t0, var_saludo
    j void.principal                # inicio del programa: ir a principal

# ============================================================
# funcion int$f
# ============================================================
int.f:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -4
    li $t0, 1                       # int12 = 1
    sw $t0, -4($fp)
    lw $v0, -4($fp)                 # return int12
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$f$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion int$suma
# ============================================================
int.suma:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -80
    lw $t0, 12($fp)                 # int13 = a
    sw $t0, -8($fp)
    lw $t0, 8($fp)                  # int14 = b
    sw $t0, -12($fp)
    lw $t0, -8($fp)                 # int15 = int13 + int14
    lw $t1, -12($fp)
    add $t2, $t0, $t1
    sw $t2, -16($fp)
    li $t0, 2                       # int16 = 2
    sw $t0, -20($fp)
    lw $t0, -16($fp)                # int17 = int15 * int16
    lw $t1, -20($fp)
    mul $t2, $t0, $t1
    sw $t2, -24($fp)
    lw $t0, -24($fp)                # r = int17
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # int18 = r
    sw $t0, -28($fp)
    lw $t0, 12($fp)                 # int19 = a
    sw $t0, -32($fp)
    lw $t0, 8($fp)                  # int20 = b
    sw $t0, -36($fp)
    lw $t0, -32($fp)                # int21 = int19 // int20
    lw $t1, -36($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -40($fp)
    lw $t0, -28($fp)                # int22 = int18 - int21
    lw $t1, -40($fp)
    sub $t2, $t0, $t1
    sw $t2, -44($fp)
    lw $t0, -44($fp)                # r = int22
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # int23 = r
    sw $t0, -48($fp)
    li $t0, 3                       # int24 = 3
    sw $t0, -52($fp)
    lw $t0, -48($fp)                # int25 = int23 mod int24
    lw $t1, -52($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -56($fp)
    lw $t0, -56($fp)                # r = int25
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # int26 = r
    sw $t0, -60($fp)
    li $t0, 2                       # int27 = 2
    sw $t0, -64($fp)
    li $t0, 3                       # int28 = 3
    sw $t0, -68($fp)
    lw $t0, -64($fp)                # int29 = int27 pot int28
    lw $t1, -68($fp)
    li $t2, 1

pot_ciclo_1:
    blez $t1, pot_fin_1
    mul $t2, $t2, $t0
    addi $t1, $t1, -1
    j pot_ciclo_1

pot_fin_1:
    sw $t2, -72($fp)
    lw $t0, -60($fp)                # int30 = int26 + int29
    lw $t1, -72($fp)
    add $t2, $t0, $t1
    sw $t2, -76($fp)
    lw $t0, -76($fp)                # r = int30
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # int31 = r
    sw $t0, -80($fp)
    lw $v0, -80($fp)                # return int31
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$suma$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion float$promedio
# ============================================================
float.promedio:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -28
    l.s $f0, 12($fp)                # float32 = x
    s.s $f0, -8($fp)
    l.s $f0, 8($fp)                 # float33 = y
    s.s $f0, -12($fp)
    l.s $f0, -8($fp)                # float34 = float32 + float33
    l.s $f2, -12($fp)
    add.s $f4, $f0, $f2
    s.s $f4, -16($fp)
    l.s $f0, -16($fp)               # total = float34
    s.s $f0, -4($fp)
    l.s $f0, -4($fp)                # float35 = total
    s.s $f0, -20($fp)
    l.s $f0, num_5                  # float36 = 2.0
    s.s $f0, -24($fp)
    l.s $f0, -20($fp)               # float37 = float35 / float36
    l.s $f2, -24($fp)
    div.s $f4, $f0, $f2
    s.s $f4, -28($fp)
    l.s $f0, -28($fp)               # return float37
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # float$promedio$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion char$inicial
# ============================================================
char.inicial:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -12
    lw $t0, 8($fp)                  # bool38 = mayus
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # if bool38 goto inicial$if$1$bloque1
    bnez $t0, inicial.if.1.bloque1
    j inicial.if.1.siguiente1       # goto inicial$if$1$siguiente1

inicial.if.1.bloque1:
    li $t0, 65                      # char39 = 'A'
    sw $t0, -8($fp)
    lw $v0, -8($fp)                 # return char39
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

inicial.if.1.siguiente1:

inicial.if.1.final:
    lw $t0, 12($fp)                 # char40 = c
    sw $t0, -12($fp)
    lw $v0, -12($fp)                # return char40
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # char$inicial$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion bool$distintos
# ============================================================
bool.distintos:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -12
    lw $t0, 12($fp)                 # int41 = a
    sw $t0, -4($fp)
    lw $t0, 8($fp)                  # int42 = b
    sw $t0, -8($fp)
    lw $t0, -4($fp)                 # bool43 = int41 != int42
    lw $t1, -8($fp)
    sne $t2, $t0, $t1
    sw $t2, -12($fp)
    lw $v0, -12($fp)                # return bool43
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # bool$distintos$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion bool$_funcionInt_
# ============================================================
bool._funcionInt_:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -48
    li $t0, 1                       # bool44 = true
    sw $t0, -8($fp)
    li $t0, 12                      # int45 = 12
    sw $t0, -12($fp)
    li $t0, 34                      # int46 = 34
    sw $t0, -16($fp)
    li $t0, 35                      # int47 = 35
    sw $t0, -20($fp)
    lw $t0, -16($fp)                # int48 = int46 + int47
    lw $t1, -20($fp)
    add $t2, $t0, $t1
    sw $t2, -24($fp)
    lw $t0, -12($fp)                # bool49 = int45 >= int48
    lw $t1, -24($fp)
    sge $t2, $t0, $t1
    sw $t2, -28($fp)
    lw $t0, -28($fp)                # bool50 = not bool49
    xori $t0, $t0, 1
    sw $t0, -32($fp)
    lw $t0, -8($fp)                 # bool51 = bool44 and bool50
    lw $t1, -32($fp)
    and $t2, $t0, $t1
    sw $t2, -36($fp)
    lw $t0, -36($fp)                # result = bool51
    sw $t0, -4($fp)

_funcionInt_.while.2.condicion:
    li $t0, 1                       # bool52 = true
    sw $t0, -40($fp)
    lw $t0, -40($fp)                # if bool52 goto _funcionInt_$while$2$bloque
    bnez $t0, _funcionInt_.while.2.bloque
    j _funcionInt_.while.2.final    # goto _funcionInt_$while$2$final

_funcionInt_.while.2.bloque:
    lw $t0, var__i_                 # int53 = _i_
    sw $t0, -44($fp)
    lw $a0, -44($fp)                # write int53
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j _funcionInt_.while.2.final    # goto _funcionInt_$while$2$final
    j _funcionInt_.while.2.condicion# goto _funcionInt_$while$2$condicion

_funcionInt_.while.2.final:
    li $t0, 1                       # bool54 = true
    sw $t0, -48($fp)
    lw $v0, -48($fp)                # return bool54
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # bool$_funcionInt_$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion void$principal
# ============================================================
void.principal:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -500
    li $t0, 10                      # int55 = 10
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # var = int55
    sw $t0, -4($fp)
    li $t0, 22                      # int56 = 22
    sw $t0, -12($fp)
    li $t0, 45                      # int57 = 45
    sw $t0, -16($fp)
    lw $t0, -12($fp)                # bool58 = int56 < int57
    lw $t1, -16($fp)
    slt $t2, $t0, $t1
    sw $t2, -20($fp)
    lw $t0, -4($fp)                 # int59 = var
    sw $t0, -24($fp)
    li $t0, 5                       # int60 = 5
    sw $t0, -28($fp)
    li $t0, 6                       # int61 = 6
    sw $t0, -32($fp)
    lw $t0, -28($fp)                # int62 = int60 // int61
    lw $t1, -32($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -36($fp)
    lw $t0, -24($fp)                # bool63 = int59 > int62
    lw $t1, -36($fp)
    sgt $t2, $t0, $t1
    sw $t2, -40($fp)
    lw $t0, -20($fp)                # bool64 = bool58 and bool63
    lw $t1, -40($fp)
    and $t2, $t0, $t1
    sw $t2, -44($fp)
    lw $t0, -44($fp)                # if bool64 goto principal$if$3$bloque1
    bnez $t0, principal.if.3.bloque1
    j principal.if.3.siguiente1     # goto principal$if$3$siguiente1

principal.if.3.bloque1:
    sw $zero, -48($fp)              # declaracion# int y
    li $t0, 97                      # char65 = 'a'
    sw $t0, -56($fp)
    lw $t0, -56($fp)                # ch33 = char65
    sw $t0, -52($fp)
    j principal.if.3.final          # goto principal$if$3$final

principal.if.3.siguiente1:
    li $t0, 1                       # bool66 = true
    sw $t0, -60($fp)
    li $t0, 0                       # bool67 = false
    sw $t0, -64($fp)
    lw $t0, -60($fp)                # bool68 = bool66 and bool67
    lw $t1, -64($fp)
    and $t2, $t0, $t1
    sw $t2, -68($fp)
    li $t0, 1                       # bool69 = true
    sw $t0, -72($fp)
    lw $t0, -68($fp)                # bool70 = bool68 or bool69
    lw $t1, -72($fp)
    or $t2, $t0, $t1
    sw $t2, -76($fp)
    lw $t0, -76($fp)                # if bool70 goto principal$if$3$bloque2
    bnez $t0, principal.if.3.bloque2
    j principal.if.3.siguiente2     # goto principal$if$3$siguiente2

principal.if.3.bloque2:
    sw $zero, -48($fp)              # declaracion# int y
    j principal.if.3.final          # goto principal$if$3$final

principal.if.3.siguiente2:
    sw $zero, -48($fp)              # declaracion# int y
    la $t0, txt_2                   # string71 = "sdff"
    sw $t0, -84($fp)
    lw $t0, -84($fp)                # str2 = string71
    sw $t0, -80($fp)

principal.if.3.final:
    sw $zero, -88($fp)              # declaracion# int x
    li $t0, 22                      # int72 = 22
    sw $t0, -104($fp)
    li $t0, 10                      # int73 = 10
    sw $t0, -108($fp)
    li $t0, 11                      # int74 = 11
    sw $t0, -112($fp)
    lw $t0, -104($fp)               # edades[0] = int72
    addiu $t9, $fp, -100
    sw $t0, 0($t9)
    lw $t0, -108($fp)               # edades[1] = int73
    addiu $t9, $fp, -100
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    lw $t0, -112($fp)               # edades[2] = int74
    addiu $t9, $fp, -100
    addiu $t9, $t9, 8
    sw $t0, 0($t9)

principal.for.4.inicializacion:
    li $t0, 0                       # int75 = 0
    sw $t0, -116($fp)
    lw $t0, -116($fp)               # x = int75
    sw $t0, -88($fp)

principal.for.4.condicion:
    j principal.for.4.bloque        # goto principal$for$4$bloque

principal.for.4.incremento:
    lw $t0, -88($fp)                # int76 = x
    sw $t0, -120($fp)
    lw $t0, -120($fp)               # int77 = int76 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -124($fp)
    lw $t0, -124($fp)               # x = int77
    sw $t0, -88($fp)
    j principal.for.4.condicion     # goto principal$for$4$condicion

principal.for.4.bloque:
    la $t0, txt_3                   # string78 = "Hola mundo"
    sw $t0, -128($fp)
    lw $a0, -128($fp)               # write string78
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -88($fp)                # int79 = x
    sw $t0, -132($fp)
    li $t0, 10                      # int80 = 10
    sw $t0, -136($fp)
    lw $t0, -132($fp)               # bool81 = int79 > int80
    lw $t1, -136($fp)
    sgt $t2, $t0, $t1
    sw $t2, -140($fp)
    lw $t0, -140($fp)               # if bool81 goto principal$if$5$bloque1
    bnez $t0, principal.if.5.bloque1
    j principal.if.5.siguiente1     # goto principal$if$5$siguiente1

principal.if.5.bloque1:
    addiu $t9, $fp, -100            # int82 = edades[2]
    addiu $t9, $t9, 8
    lw $t0, 0($t9)
    sw $t0, -144($fp)
    li $t0, 1                       # int83 = 1
    sw $t0, -148($fp)
    lw $t0, -144($fp)               # int84 = int82 + int83
    lw $t1, -148($fp)
    add $t2, $t0, $t1
    sw $t2, -152($fp)
    lw $t0, -152($fp)               # edades[2] = int84
    addiu $t9, $fp, -100
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    j principal.for.4.final         # goto principal$for$4$final

principal.if.5.siguiente1:

principal.if.5.final:
    j principal.for.4.incremento    # goto principal$for$4$incremento

principal.for.4.final:
    jal int.f                       # int85 = call int$f, 0
    sw $v0, -160($fp)
    lw $t0, -160($fp)               # uno = int85
    sw $t0, -156($fp)
    li $t0, 3                       # int86 = 3
    sw $t0, -168($fp)
    li $t0, 4                       # int87 = 4
    sw $t0, -172($fp)
    lw $t0, -168($fp)               # param int86
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -172($fp)               # param int87
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.suma                    # int88 = call int$suma, 2
    addiu $sp, $sp, 8
    sw $v0, -176($fp)
    lw $t0, -176($fp)               # s = int88
    sw $t0, -164($fp)
    lw $t0, var__i_                 # int89 = _i_
    sw $t0, -180($fp)
    li $t0, 2                       # int90 = 2
    sw $t0, -184($fp)
    lw $t0, -180($fp)               # param int89
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -184($fp)               # param int90
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.suma                    # int91 = call int$suma, 2
    addiu $sp, $sp, 8
    sw $v0, -188($fp)
    l.s $f0, num_6                  # float92 = 7.5
    s.s $f0, -196($fp)
    l.s $f0, var_pi                 # float93 = pi
    s.s $f0, -200($fp)
    l.s $f0, -196($fp)              # param float92
    addiu $sp, $sp, -4
    s.s $f0, 0($sp)
    l.s $f0, -200($fp)              # param float93
    addiu $sp, $sp, -4
    s.s $f0, 0($sp)
    jal float.promedio              # float94 = call float$promedio, 2
    addiu $sp, $sp, 8
    s.s $f0, -204($fp)
    l.s $f0, -204($fp)              # prom = float94
    s.s $f0, -192($fp)
    lw $t0, var_letrica             # char95 = letrica
    sw $t0, -212($fp)
    lw $t0, var_activo              # bool96 = activo
    sw $t0, -216($fp)
    lw $t0, -212($fp)               # param char95
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -216($fp)               # param bool96
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal char.inicial                # char97 = call char$inicial, 2
    addiu $sp, $sp, 8
    sw $v0, -220($fp)
    lw $t0, -220($fp)               # c = char97
    sw $t0, -208($fp)
    lw $t0, -164($fp)               # int98 = s
    sw $t0, -228($fp)
    li $t0, 7                       # int99 = 7
    sw $t0, -232($fp)
    lw $t0, -228($fp)               # param int98
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -232($fp)               # param int99
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal bool.distintos              # bool100 = call bool$distintos, 2
    addiu $sp, $sp, 8
    sw $v0, -236($fp)
    lw $t0, -236($fp)               # dif = bool100
    sw $t0, -224($fp)
    jal bool._funcionInt_           # bool101 = call bool$_funcionInt_, 0
    sw $v0, -244($fp)
    lw $t0, -244($fp)               # resultadoBool = bool101
    sw $t0, -240($fp)
    li $v0, 5                       # read var
    syscall
    sw $v0, -4($fp)
    li $v0, 12                      # read c
    syscall
    move $t0, $v0
    sb $t0, -208($fp)
    li $v0, 5                       # read _arr_[0]
    syscall
    la $t9, var__arr_
    sw $v0, 0($t9)
    lw $t0, -164($fp)               # int102 = s
    sw $t0, -248($fp)
    lw $a0, -248($fp)               # write int102
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, -192($fp)              # float103 = prom
    s.s $f0, -252($fp)
    l.s $f12, -252($fp)             # write float103
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, var_saludo              # string104 = saludo
    sw $t0, -256($fp)
    lw $a0, -256($fp)               # write string104
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, -5                      # int105 = -5
    sw $t0, -260($fp)
    lw $a0, -260($fp)               # write int105
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 3                       # int106 = 3
    sw $t0, -268($fp)
    lw $t0, -268($fp)               # contador = int106
    sw $t0, -264($fp)

principal.while.6.condicion:
    lw $t0, -264($fp)               # int107 = contador
    sw $t0, -272($fp)
    li $t0, 0                       # int108 = 0
    sw $t0, -276($fp)
    lw $t0, -272($fp)               # bool109 = int107 > int108
    lw $t1, -276($fp)
    sgt $t2, $t0, $t1
    sw $t2, -280($fp)
    lw $t0, -280($fp)               # if bool109 goto principal$while$6$bloque
    bnez $t0, principal.while.6.bloque
    j principal.while.6.final       # goto principal$while$6$final

principal.while.6.bloque:
    lw $t0, -264($fp)               # int110 = contador
    sw $t0, -284($fp)
    li $t0, 3                       # int111 = 3
    sw $t0, -288($fp)
    lw $t0, -284($fp)               # bool112 = int110 == int111
    lw $t1, -288($fp)
    seq $t2, $t0, $t1
    sw $t2, -292($fp)
    lw $t0, -292($fp)               # if bool112 goto principal$if$7$bloque1
    bnez $t0, principal.if.7.bloque1
    j principal.if.7.siguiente1     # goto principal$if$7$siguiente1

principal.if.7.bloque1:
    la $t0, txt_4                   # string113 = "tres"
    sw $t0, -296($fp)
    lw $a0, -296($fp)               # write string113
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.if.7.final          # goto principal$if$7$final

principal.if.7.siguiente1:
    lw $t0, -264($fp)               # int114 = contador
    sw $t0, -300($fp)
    li $t0, 2                       # int115 = 2
    sw $t0, -304($fp)
    lw $t0, -300($fp)               # bool116 = int114 == int115
    lw $t1, -304($fp)
    seq $t2, $t0, $t1
    sw $t2, -308($fp)
    lw $t0, -308($fp)               # if bool116 goto principal$if$7$bloque2
    bnez $t0, principal.if.7.bloque2
    j principal.if.7.siguiente2     # goto principal$if$7$siguiente2

principal.if.7.bloque2:
    la $t0, txt_5                   # string117 = "dos"
    sw $t0, -312($fp)
    lw $a0, -312($fp)               # write string117
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.if.7.final          # goto principal$if$7$final

principal.if.7.siguiente2:
    lw $t0, -264($fp)               # int118 = contador
    sw $t0, -316($fp)
    li $t0, 1                       # int119 = 1
    sw $t0, -320($fp)
    lw $t0, -316($fp)               # bool120 = int118 <= int119
    lw $t1, -320($fp)
    sle $t2, $t0, $t1
    sw $t2, -324($fp)
    lw $t0, -324($fp)               # if bool120 goto principal$if$7$bloque3
    bnez $t0, principal.if.7.bloque3
    j principal.if.7.siguiente3     # goto principal$if$7$siguiente3

principal.if.7.bloque3:
    la $t0, txt_6                   # string121 = "uno"
    sw $t0, -328($fp)
    lw $a0, -328($fp)               # write string121
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.7.siguiente3:

principal.if.7.final:
    lw $t0, -264($fp)               # int122 = contador
    sw $t0, -332($fp)
    li $t0, 1                       # int123 = 1
    sw $t0, -336($fp)
    lw $t0, -332($fp)               # int124 = int122 - int123
    lw $t1, -336($fp)
    sub $t2, $t0, $t1
    sw $t2, -340($fp)
    lw $t0, -340($fp)               # contador = int124
    sw $t0, -264($fp)
    j principal.while.6.condicion   # goto principal$while$6$condicion

principal.while.6.final:
    lw $t0, -208($fp)               # char125 = c
    sw $t0, -344($fp)
    li $t0, 65                      # char126 = 'A'
    sw $t0, -348($fp)
    lw $t0, -344($fp)               # bool127 = char125 == char126
    lw $t1, -348($fp)
    seq $t2, $t0, $t1
    sw $t2, -352($fp)
    lw $t0, -352($fp)               # bool128 = not bool127
    xori $t0, $t0, 1
    sw $t0, -356($fp)
    lw $t0, -356($fp)               # if bool128 goto principal$if$8$bloque1
    bnez $t0, principal.if.8.bloque1
    j principal.if.8.siguiente1     # goto principal$if$8$siguiente1

principal.if.8.bloque1:
    la $t0, txt_7                   # string129 = "no es A"
    sw $t0, -360($fp)
    lw $a0, -360($fp)               # write string129
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.8.siguiente1:

principal.if.8.final:

principal.for.9.inicializacion:
    li $t0, 0                       # int130 = 0
    sw $t0, -368($fp)
    lw $t0, -368($fp)               # i = int130
    sw $t0, -364($fp)

principal.for.9.condicion:
    lw $t0, -364($fp)               # int131 = i
    sw $t0, -372($fp)
    li $t0, 3                       # int132 = 3
    sw $t0, -376($fp)
    lw $t0, -372($fp)               # bool133 = int131 < int132
    lw $t1, -376($fp)
    slt $t2, $t0, $t1
    sw $t2, -380($fp)
    lw $t0, -380($fp)               # if bool133 goto principal$for$9$bloque
    bnez $t0, principal.for.9.bloque
    j principal.for.9.final         # goto principal$for$9$final

principal.for.9.incremento:
    lw $t0, -364($fp)               # int134 = i
    sw $t0, -384($fp)
    lw $t0, -384($fp)               # int135 = int134 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -388($fp)
    lw $t0, -388($fp)               # i = int135
    sw $t0, -364($fp)
    j principal.for.9.condicion     # goto principal$for$9$condicion

principal.for.9.bloque:
    la $t9, var_arr                 # int136 = arr[i]
    lw $t8, -364($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -392($fp)
    li $t0, 2                       # int137 = 2
    sw $t0, -396($fp)
    lw $t0, -392($fp)               # int138 = int136 * int137
    lw $t1, -396($fp)
    mul $t2, $t0, $t1
    sw $t2, -400($fp)
    lw $t0, -400($fp)               # arr[i] = int138
    la $t9, var_arr
    lw $t8, -364($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    sw $t0, 0($t9)
    la $t9, var_notas               # float139 = notas[i]
    lw $t8, -364($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    l.s $f0, 0($t9)
    s.s $f0, -404($fp)
    l.s $f0, num_5                  # float140 = 2.0
    s.s $f0, -408($fp)
    l.s $f0, -404($fp)              # float141 = float139 / float140
    l.s $f2, -408($fp)
    div.s $f4, $f0, $f2
    s.s $f4, -412($fp)
    l.s $f0, -412($fp)              # notas[i] = float141
    la $t9, var_notas
    lw $t8, -364($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    s.s $f0, 0($t9)
    lw $t0, -364($fp)               # int142 = i
    sw $t0, -416($fp)
    li $t0, 1                       # int143 = 1
    sw $t0, -420($fp)
    lw $t0, -416($fp)               # int144 = int142 + int143
    lw $t1, -420($fp)
    add $t2, $t0, $t1
    sw $t2, -424($fp)
    la $t9, var_arr                 # int145 = arr[i]
    lw $t8, -364($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -428($fp)
    lw $t0, -428($fp)               # _arr_[int144] = int145
    la $t9, var__arr_
    lw $t8, -424($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    sw $t0, 0($t9)
    j principal.for.9.incremento    # goto principal$for$9$incremento

principal.for.9.final:
    sw $zero, -432($fp)             # declaracion# int j

principal.for.10.inicializacion:
    li $t0, 10                      # int146 = 10
    sw $t0, -436($fp)
    lw $t0, -436($fp)               # j = int146
    sw $t0, -432($fp)

principal.for.10.condicion:
    lw $t0, -432($fp)               # int147 = j
    sw $t0, -440($fp)
    li $t0, 0                       # int148 = 0
    sw $t0, -444($fp)
    lw $t0, -440($fp)               # bool149 = int147 >= int148
    lw $t1, -444($fp)
    sge $t2, $t0, $t1
    sw $t2, -448($fp)
    lw $t0, -448($fp)               # if bool149 goto principal$for$10$bloque
    bnez $t0, principal.for.10.bloque
    j principal.for.10.final        # goto principal$for$10$final

principal.for.10.incremento:
    lw $t0, -432($fp)               # int150 = j
    sw $t0, -452($fp)
    lw $t0, -452($fp)               # int151 = int150 - 1
    li $t1, 1
    sub $t2, $t0, $t1
    sw $t2, -456($fp)
    lw $t0, -456($fp)               # j = int151
    sw $t0, -432($fp)
    j principal.for.10.condicion    # goto principal$for$10$condicion

principal.for.10.bloque:
    lw $t0, -432($fp)               # int152 = j
    sw $t0, -460($fp)
    li $t0, 5                       # int153 = 5
    sw $t0, -464($fp)
    lw $t0, -460($fp)               # bool154 = int152 < int153
    lw $t1, -464($fp)
    slt $t2, $t0, $t1
    sw $t2, -468($fp)
    lw $t0, var_activo              # bool155 = activo
    sw $t0, -472($fp)
    lw $t0, -468($fp)               # bool156 = bool154 or bool155
    lw $t1, -472($fp)
    or $t2, $t0, $t1
    sw $t2, -476($fp)
    lw $t0, -476($fp)               # if bool156 goto principal$if$11$bloque1
    bnez $t0, principal.if.11.bloque1
    j principal.if.11.siguiente1    # goto principal$if$11$siguiente1

principal.if.11.bloque1:

principal.while.12.condicion:
    lw $t0, -432($fp)               # int157 = j
    sw $t0, -480($fp)
    li $t0, 2                       # int158 = 2
    sw $t0, -484($fp)
    lw $t0, -480($fp)               # bool159 = int157 > int158
    lw $t1, -484($fp)
    sgt $t2, $t0, $t1
    sw $t2, -488($fp)
    lw $t0, -488($fp)               # if bool159 goto principal$while$12$bloque
    bnez $t0, principal.while.12.bloque
    j principal.while.12.final      # goto principal$while$12$final

principal.while.12.bloque:
    lw $t0, -432($fp)               # int160 = j
    sw $t0, -492($fp)
    li $t0, 1                       # int161 = 1
    sw $t0, -496($fp)
    lw $t0, -492($fp)               # int162 = int160 - int161
    lw $t1, -496($fp)
    sub $t2, $t0, $t1
    sw $t2, -500($fp)
    lw $t0, -500($fp)               # j = int162
    sw $t0, -432($fp)
    j principal.while.12.final      # goto principal$while$12$final
    j principal.while.12.condicion  # goto principal$while$12$condicion

principal.while.12.final:
    j principal.for.10.final        # goto principal$for$10$final

principal.if.11.siguiente1:

principal.if.11.final:
    j principal.for.10.incremento   # goto principal$for$10$incremento

principal.for.10.final:
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
