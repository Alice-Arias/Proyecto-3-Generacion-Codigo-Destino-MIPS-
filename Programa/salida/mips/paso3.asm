.data
g_int1: .word 0
g_a: .word 0
g_int2: .word 0
g_b: .word 0
g_bool3: .word 0
g_activo: .word 0
g_int4: .word 0
g_int5: .word 0
g_bool6: .word 0
g_int7: .word 0
g_int8: .word 0
g_int9: .word 0
g_int10: .word 0
g_bool11: .word 0
g_int12: .word 0
g_int13: .word 0
g_bool14: .word 0
g_bool15: .word 0
g_int16: .word 0
g_int17: .word 0
g_int18: .word 0
g_int19: .word 0
g_bool20: .word 0
g_int21: .word 0
g_int22: .word 0
g_int23: .word 0
g_bool24: .word 0
g_int25: .word 0
g_int26: .word 0
g_int27: .word 0
g_int28: .word 0
g_bool29: .word 0
g_bool30: .word 0
g_int31: .word 0
g_int32: .word 0
g_int33: .word 0
g_bool34: .word 0
g_bool35: .word 0
g_bool36: .word 0
g_int37: .word 0
g_int38: .word 0
g_c: .word 0
g_int39: .word 0
g_int40: .word 0
g_bool41: .word 0
g_int42: .word 0
g_int43: .word 0
g_int44: .word 0
g_int45: .word 0
g_int46: .word 0
g_i: .word 0
g_int47: .word 0
g_int48: .word 0
g_bool49: .word 0
g_int50: .word 0
g_int51: .word 0
g_int52: .word 0
g_int53: .word 0
g_bool54: .word 0
g_int55: .word 0

.text
.globl main
main:
    # void$principal:
void.principal:
    # declaracionPrevia# int a
    # int1 = 10
    li $t0, 10
    sw $t0, g_int1
    # a = int1
    lw $t0, g_int1
    sw $t0, g_a
    # declaracionPrevia# int b
    # int2 = 3
    li $t0, 3
    sw $t0, g_int2
    # b = int2
    lw $t0, g_int2
    sw $t0, g_b
    # declaracionPrevia# bool activo
    # bool3 = true
    li $t0, 1
    sw $t0, g_bool3
    # activo = bool3
    lw $t0, g_bool3
    sw $t0, g_activo
    # int4 = a
    lw $t0, g_a
    sw $t0, g_int4
    # int5 = b
    lw $t0, g_b
    sw $t0, g_int5
    # bool6 = int4 > int5
    lw $t0, g_int4
    lw $t1, g_int5
    sgt $t2, $t0, $t1
    sw $t2, g_bool6
    # if bool6 goto principal$if$1$bloque1
    lw $t0, g_bool6
    bnez $t0, principal.if.1.bloque1
    # goto principal$if$1$siguiente1
    j principal.if.1.siguiente1
    # principal$if$1$bloque1:
principal.if.1.bloque1:
    # int7 = 1
    li $t0, 1
    sw $t0, g_int7
    # write int7
    lw $a0, g_int7
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # goto principal$if$1$final
    j principal.if.1.final
    # principal$if$1$siguiente1:
principal.if.1.siguiente1:
    # int8 = 0
    li $t0, 0
    sw $t0, g_int8
    # write int8
    lw $a0, g_int8
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # principal$if$1$final:
principal.if.1.final:
    # int9 = a
    lw $t0, g_a
    sw $t0, g_int9
    # int10 = 10
    li $t0, 10
    sw $t0, g_int10
    # bool11 = int9 == int10
    lw $t0, g_int9
    lw $t1, g_int10
    seq $t2, $t0, $t1
    sw $t2, g_bool11
    # int12 = b
    lw $t0, g_b
    sw $t0, g_int12
    # int13 = 3
    li $t0, 3
    sw $t0, g_int13
    # bool14 = int12 != int13
    lw $t0, g_int12
    lw $t1, g_int13
    sne $t2, $t0, $t1
    sw $t2, g_bool14
    # bool15 = bool11 and bool14
    lw $t0, g_bool11
    lw $t1, g_bool14
    and $t2, $t0, $t1
    sw $t2, g_bool15
    # if bool15 goto principal$if$2$bloque1
    lw $t0, g_bool15
    bnez $t0, principal.if.2.bloque1
    # goto principal$if$2$siguiente1
    j principal.if.2.siguiente1
    # principal$if$2$bloque1:
principal.if.2.bloque1:
    # int16 = 1
    li $t0, 1
    sw $t0, g_int16
    # write int16
    lw $a0, g_int16
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # goto principal$if$2$final
    j principal.if.2.final
    # principal$if$2$siguiente1:
principal.if.2.siguiente1:
    # int17 = 2
    li $t0, 2
    sw $t0, g_int17
    # write int17
    lw $a0, g_int17
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # principal$if$2$final:
principal.if.2.final:
    # int18 = a
    lw $t0, g_a
    sw $t0, g_int18
    # int19 = 5
    li $t0, 5
    sw $t0, g_int19
    # bool20 = int18 < int19
    lw $t0, g_int18
    lw $t1, g_int19
    slt $t2, $t0, $t1
    sw $t2, g_bool20
    # if bool20 goto principal$if$3$bloque1
    lw $t0, g_bool20
    bnez $t0, principal.if.3.bloque1
    # goto principal$if$3$siguiente1
    j principal.if.3.siguiente1
    # principal$if$3$bloque1:
principal.if.3.bloque1:
    # int21 = 1
    li $t0, 1
    sw $t0, g_int21
    # write int21
    lw $a0, g_int21
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # goto principal$if$3$final
    j principal.if.3.final
    # principal$if$3$siguiente1:
principal.if.3.siguiente1:
    # int22 = a
    lw $t0, g_a
    sw $t0, g_int22
    # int23 = 10
    li $t0, 10
    sw $t0, g_int23
    # bool24 = int22 <= int23
    lw $t0, g_int22
    lw $t1, g_int23
    sle $t2, $t0, $t1
    sw $t2, g_bool24
    # if bool24 goto principal$if$3$bloque2
    lw $t0, g_bool24
    bnez $t0, principal.if.3.bloque2
    # goto principal$if$3$siguiente2
    j principal.if.3.siguiente2
    # principal$if$3$bloque2:
principal.if.3.bloque2:
    # int25 = 2
    li $t0, 2
    sw $t0, g_int25
    # write int25
    lw $a0, g_int25
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # goto principal$if$3$final
    j principal.if.3.final
    # principal$if$3$siguiente2:
principal.if.3.siguiente2:
    # int26 = 3
    li $t0, 3
    sw $t0, g_int26
    # write int26
    lw $a0, g_int26
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # principal$if$3$final:
principal.if.3.final:
    # int27 = a
    lw $t0, g_a
    sw $t0, g_int27
    # int28 = b
    lw $t0, g_b
    sw $t0, g_int28
    # bool29 = int27 < int28
    lw $t0, g_int27
    lw $t1, g_int28
    slt $t2, $t0, $t1
    sw $t2, g_bool29
    # bool30 = not bool29
    lw $t0, g_bool29
    xori $t0, $t0, 1
    sw $t0, g_bool30
    # if bool30 goto principal$if$4$bloque1
    lw $t0, g_bool30
    bnez $t0, principal.if.4.bloque1
    # goto principal$if$4$siguiente1
    j principal.if.4.siguiente1
    # principal$if$4$bloque1:
principal.if.4.bloque1:
    # int31 = 4
    li $t0, 4
    sw $t0, g_int31
    # write int31
    lw $a0, g_int31
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # principal$if$4$siguiente1:
principal.if.4.siguiente1:
    # principal$if$4$final:
principal.if.4.final:
    # int32 = a
    lw $t0, g_a
    sw $t0, g_int32
    # int33 = b
    lw $t0, g_b
    sw $t0, g_int33
    # bool34 = int32 < int33
    lw $t0, g_int32
    lw $t1, g_int33
    slt $t2, $t0, $t1
    sw $t2, g_bool34
    # bool35 = activo
    lw $t0, g_activo
    sw $t0, g_bool35
    # bool36 = bool34 or bool35
    lw $t0, g_bool34
    lw $t1, g_bool35
    or $t2, $t0, $t1
    sw $t2, g_bool36
    # if bool36 goto principal$if$5$bloque1
    lw $t0, g_bool36
    bnez $t0, principal.if.5.bloque1
    # goto principal$if$5$siguiente1
    j principal.if.5.siguiente1
    # principal$if$5$bloque1:
principal.if.5.bloque1:
    # int37 = 5
    li $t0, 5
    sw $t0, g_int37
    # write int37
    lw $a0, g_int37
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # principal$if$5$siguiente1:
principal.if.5.siguiente1:
    # principal$if$5$final:
principal.if.5.final:
    # declaracionPrevia# int c
    # int38 = 3
    li $t0, 3
    sw $t0, g_int38
    # c = int38
    lw $t0, g_int38
    sw $t0, g_c
    # principal$while$6$condicion:
principal.while.6.condicion:
    # int39 = c
    lw $t0, g_c
    sw $t0, g_int39
    # int40 = 0
    li $t0, 0
    sw $t0, g_int40
    # bool41 = int39 > int40
    lw $t0, g_int39
    lw $t1, g_int40
    sgt $t2, $t0, $t1
    sw $t2, g_bool41
    # if bool41 goto principal$while$6$bloque
    lw $t0, g_bool41
    bnez $t0, principal.while.6.bloque
    # goto principal$while$6$final
    j principal.while.6.final
    # principal$while$6$bloque:
principal.while.6.bloque:
    # int42 = c
    lw $t0, g_c
    sw $t0, g_int42
    # write int42
    lw $a0, g_int42
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int43 = c
    lw $t0, g_c
    sw $t0, g_int43
    # int44 = 1
    li $t0, 1
    sw $t0, g_int44
    # int45 = int43 - int44
    lw $t0, g_int43
    lw $t1, g_int44
    sub $t2, $t0, $t1
    sw $t2, g_int45
    # c = int45
    lw $t0, g_int45
    sw $t0, g_c
    # goto principal$while$6$condicion
    j principal.while.6.condicion
    # principal$while$6$final:
principal.while.6.final:
    # principal$for$7$inicializacion:
principal.for.7.inicializacion:
    # declaracionPrevia# int i
    # int46 = 0
    li $t0, 0
    sw $t0, g_int46
    # i = int46
    lw $t0, g_int46
    sw $t0, g_i
    # principal$for$7$condicion:
principal.for.7.condicion:
    # int47 = i
    lw $t0, g_i
    sw $t0, g_int47
    # int48 = 10
    li $t0, 10
    sw $t0, g_int48
    # bool49 = int47 < int48
    lw $t0, g_int47
    lw $t1, g_int48
    slt $t2, $t0, $t1
    sw $t2, g_bool49
    # if bool49 goto principal$for$7$bloque
    lw $t0, g_bool49
    bnez $t0, principal.for.7.bloque
    # goto principal$for$7$final
    j principal.for.7.final
    # principal$for$7$incremento:
principal.for.7.incremento:
    # int50 = i
    lw $t0, g_i
    sw $t0, g_int50
    # int51 = int50 + 1
    lw $t0, g_int50
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, g_int51
    # i = int51
    lw $t0, g_int51
    sw $t0, g_i
    # goto principal$for$7$condicion
    j principal.for.7.condicion
    # principal$for$7$bloque:
principal.for.7.bloque:
    # int52 = i
    lw $t0, g_i
    sw $t0, g_int52
    # int53 = 3
    li $t0, 3
    sw $t0, g_int53
    # bool54 = int52 == int53
    lw $t0, g_int52
    lw $t1, g_int53
    seq $t2, $t0, $t1
    sw $t2, g_bool54
    # if bool54 goto principal$if$8$bloque1
    lw $t0, g_bool54
    bnez $t0, principal.if.8.bloque1
    # goto principal$if$8$siguiente1
    j principal.if.8.siguiente1
    # principal$if$8$bloque1:
principal.if.8.bloque1:
    # goto principal$for$7$final
    j principal.for.7.final
    # principal$if$8$siguiente1:
principal.if.8.siguiente1:
    # principal$if$8$final:
principal.if.8.final:
    # int55 = i
    lw $t0, g_i
    sw $t0, g_int55
    # write int55
    lw $a0, g_int55
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # goto principal$for$7$incremento
    j principal.for.7.incremento
    # principal$for$7$final:
principal.for.7.final:
    # return
    # void$principal$final
    li $v0, 10
    syscall
