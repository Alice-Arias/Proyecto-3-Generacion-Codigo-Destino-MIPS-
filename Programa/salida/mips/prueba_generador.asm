.data

# ---------- variables del programa ----------
var_letrica: .word 0
.align 2
var__arr_: .space 80
var__i_: .word 0
.align 2
var_arr: .space 12

# ---------- temporales del codigo intermedio ----------
tmp_char1: .word 0
tmp_int2: .word 0
tmp_int3: .word 0
tmp_int4: .word 0
tmp_int5: .word 0

# ---------- textos fijos ----------
txt_1: .asciiz "sdff"
txt_2: .asciiz "Hola mundo"
txt_3: .asciiz "fin del programa"

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
    li $t0, 1                       # int6 = 1
    sw $t0, -4($fp)
    lw $v0, -4($fp)                 # return int6
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
# funcion bool$_funcionInt_
# ============================================================
bool._funcionInt_:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -48
    li $t0, 1                       # bool7 = true
    sw $t0, -8($fp)
    li $t0, 12                      # int8 = 12
    sw $t0, -12($fp)
    li $t0, 34                      # int9 = 34
    sw $t0, -16($fp)
    li $t0, 35                      # int10 = 35
    sw $t0, -20($fp)
    lw $t0, -16($fp)                # int11 = int9 + int10
    lw $t1, -20($fp)
    add $t2, $t0, $t1
    sw $t2, -24($fp)
    lw $t0, -12($fp)                # bool12 = int8 >= int11
    lw $t1, -24($fp)
    sge $t2, $t0, $t1
    sw $t2, -28($fp)
    lw $t0, -28($fp)                # bool13 = not bool12
    xori $t0, $t0, 1
    sw $t0, -32($fp)
    lw $t0, -8($fp)                 # bool14 = bool7 and bool13
    lw $t1, -32($fp)
    and $t2, $t0, $t1
    sw $t2, -36($fp)
    lw $t0, -36($fp)                # result = bool14
    sw $t0, -4($fp)

_funcionInt_.while.1.condicion:
    li $t0, 1                       # bool15 = true
    sw $t0, -40($fp)
    lw $t0, -40($fp)                # if bool15 goto _funcionInt_$while$1$bloque
    bnez $t0, _funcionInt_.while.1.bloque
    j _funcionInt_.while.1.final    # goto _funcionInt_$while$1$final

_funcionInt_.while.1.bloque:
    lw $t0, var__i_                 # int16 = _i_
    sw $t0, -44($fp)
    lw $a0, -44($fp)                # write int16
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j _funcionInt_.while.1.final    # goto _funcionInt_$while$1$final
    j _funcionInt_.while.1.condicion# goto _funcionInt_$while$1$condicion

_funcionInt_.while.1.final:
    li $t0, 1                       # bool17 = true
    sw $t0, -48($fp)
    lw $v0, -48($fp)                # return bool17
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
# funcion bool$_func2_
# ============================================================
bool._func2_:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -28
    li $t0, 22                      # int18 = 22
    sw $t0, -8($fp)
    li $t0, 45                      # int19 = 45
    sw $t0, -12($fp)
    lw $t0, -8($fp)                 # bool20 = int18 < int19
    lw $t1, -12($fp)
    slt $t2, $t0, $t1
    sw $t2, -16($fp)
    lw $t0, -16($fp)                # x = bool20
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # bool21 = x
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # if bool21 goto _func2_$if$2$bloque1
    bnez $t0, _func2_.if.2.bloque1
    j _func2_.if.2.siguiente1       # goto _func2_$if$2$siguiente1

_func2_.if.2.bloque1:
    sw $zero, -24($fp)              # declaracion# int y

_func2_.if.2.siguiente1:

_func2_.if.2.final:
    lw $t0, -4($fp)                 # bool22 = x
    sw $t0, -28($fp)
    lw $v0, -28($fp)                # return bool22
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # bool$_func2_$final
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
    addiu $sp, $sp, -212
    li $t0, 10                      # int23 = 10
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # var = int23
    sw $t0, -4($fp)
    li $t0, 22                      # int24 = 22
    sw $t0, -12($fp)
    li $t0, 45                      # int25 = 45
    sw $t0, -16($fp)
    lw $t0, -12($fp)                # bool26 = int24 < int25
    lw $t1, -16($fp)
    slt $t2, $t0, $t1
    sw $t2, -20($fp)
    lw $t0, -4($fp)                 # int27 = var
    sw $t0, -24($fp)
    li $t0, 5                       # int28 = 5
    sw $t0, -28($fp)
    li $t0, 6                       # int29 = 6
    sw $t0, -32($fp)
    lw $t0, -28($fp)                # int30 = int28 // int29
    lw $t1, -32($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -36($fp)
    lw $t0, -24($fp)                # bool31 = int27 > int30
    lw $t1, -36($fp)
    sgt $t2, $t0, $t1
    sw $t2, -40($fp)
    lw $t0, -20($fp)                # bool32 = bool26 and bool31
    lw $t1, -40($fp)
    and $t2, $t0, $t1
    sw $t2, -44($fp)
    lw $t0, -44($fp)                # if bool32 goto principal$if$3$bloque1
    bnez $t0, principal.if.3.bloque1
    j principal.if.3.siguiente1     # goto principal$if$3$siguiente1

principal.if.3.bloque1:
    sw $zero, -48($fp)              # declaracion# int y
    li $t0, 97                      # char33 = 'a'
    sw $t0, -56($fp)
    lw $t0, -56($fp)                # ch33 = char33
    sw $t0, -52($fp)
    j principal.if.3.final          # goto principal$if$3$final

principal.if.3.siguiente1:
    li $t0, 1                       # bool34 = true
    sw $t0, -60($fp)
    li $t0, 0                       # bool35 = false
    sw $t0, -64($fp)
    lw $t0, -60($fp)                # bool36 = bool34 and bool35
    lw $t1, -64($fp)
    and $t2, $t0, $t1
    sw $t2, -68($fp)
    li $t0, 1                       # bool37 = true
    sw $t0, -72($fp)
    lw $t0, -68($fp)                # bool38 = bool36 or bool37
    lw $t1, -72($fp)
    or $t2, $t0, $t1
    sw $t2, -76($fp)
    lw $t0, -76($fp)                # if bool38 goto principal$if$3$bloque2
    bnez $t0, principal.if.3.bloque2
    j principal.if.3.siguiente2     # goto principal$if$3$siguiente2

principal.if.3.bloque2:
    sw $zero, -48($fp)              # declaracion# int y
    j principal.if.3.final          # goto principal$if$3$final

principal.if.3.siguiente2:
    sw $zero, -48($fp)              # declaracion# int y
    la $t0, txt_1                   # string39 = "sdff"
    sw $t0, -84($fp)
    lw $t0, -84($fp)                # str2 = string39
    sw $t0, -80($fp)

principal.if.3.final:
    sw $zero, -88($fp)              # declaracion# int x
    li $t0, 22                      # int40 = 22
    sw $t0, -104($fp)
    li $t0, 10                      # int41 = 10
    sw $t0, -108($fp)
    li $t0, 11                      # int42 = 11
    sw $t0, -112($fp)
    lw $t0, -104($fp)               # edades[0] = int40
    addiu $t9, $fp, -100
    sw $t0, 0($t9)
    lw $t0, -108($fp)               # edades[1] = int41
    addiu $t9, $fp, -100
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    lw $t0, -112($fp)               # edades[2] = int42
    addiu $t9, $fp, -100
    addiu $t9, $t9, 8
    sw $t0, 0($t9)

principal.for.4.inicializacion:
    li $t0, 0                       # int43 = 0
    sw $t0, -116($fp)
    lw $t0, -116($fp)               # x = int43
    sw $t0, -88($fp)

principal.for.4.condicion:
    j principal.for.4.bloque        # goto principal$for$4$bloque

principal.for.4.incremento:
    lw $t0, -88($fp)                # int44 = x
    sw $t0, -120($fp)
    lw $t0, -120($fp)               # int45 = int44 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -124($fp)
    lw $t0, -124($fp)               # x = int45
    sw $t0, -88($fp)
    j principal.for.4.condicion     # goto principal$for$4$condicion

principal.for.4.bloque:
    la $t0, txt_2                   # string46 = "Hola mundo"
    sw $t0, -128($fp)
    lw $a0, -128($fp)               # write string46
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -88($fp)                # int47 = x
    sw $t0, -132($fp)
    li $t0, 10                      # int48 = 10
    sw $t0, -136($fp)
    lw $t0, -132($fp)               # bool49 = int47 > int48
    lw $t1, -136($fp)
    sgt $t2, $t0, $t1
    sw $t2, -140($fp)
    lw $t0, -140($fp)               # if bool49 goto principal$if$5$bloque1
    bnez $t0, principal.if.5.bloque1
    j principal.if.5.siguiente1     # goto principal$if$5$siguiente1

principal.if.5.bloque1:
    addiu $t9, $fp, -100            # int50 = edades[2]
    addiu $t9, $t9, 8
    lw $t0, 0($t9)
    sw $t0, -144($fp)
    li $t0, 1                       # int51 = 1
    sw $t0, -148($fp)
    lw $t0, -144($fp)               # int52 = int50 + int51
    lw $t1, -148($fp)
    add $t2, $t0, $t1
    sw $t2, -152($fp)
    lw $t0, -152($fp)               # edades[2] = int52
    addiu $t9, $fp, -100
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    j principal.for.4.final         # goto principal$for$4$final

principal.if.5.siguiente1:

principal.if.5.final:
    j principal.for.4.incremento    # goto principal$for$4$incremento

principal.for.4.final:
    li $v0, 5                       # read filas[0]
    syscall
    addiu $t9, $fp, -172
    sw $v0, 0($t9)
    li $t0, 3                       # int53 = 3
    sw $t0, -176($fp)
    lw $t0, var__i_                 # int54 = _i_
    sw $t0, -180($fp)
    lw $t0, -176($fp)               # int55 = int53 + int54
    lw $t1, -180($fp)
    add $t2, $t0, $t1
    sw $t2, -184($fp)
    li $t0, -1                      # int56 = -1
    sw $t0, -188($fp)
    li $t0, 5                       # int57 = 5
    sw $t0, -192($fp)
    lw $t0, -188($fp)               # int58 = int56 + int57
    lw $t1, -192($fp)
    add $t2, $t0, $t1
    sw $t2, -196($fp)
    lw $t0, -196($fp)               # _arr_[int55] = int58
    la $t9, var__arr_
    lw $t8, -184($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    sw $t0, 0($t9)
    li $t0, 1                       # bool59 = true
    sw $t0, -200($fp)
    li $t0, 5                       # int60 = 5
    sw $t0, -204($fp)
    lw $t0, -200($fp)               # param bool59
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -204($fp)               # param int60
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal bool._func2_                # bool61 = call bool$_func2_, 2
    addiu $sp, $sp, 8
    sw $v0, -208($fp)
    la $t0, txt_3                   # string62 = "fin del programa"
    sw $t0, -212($fp)
    lw $a0, -212($fp)               # write string62
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
