.data

.text
.globl main
main:
    j void.principal                # inicio del programa: ir a principal

# ============================================================
# funcion void$principal
# ============================================================
void.principal:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -240
    li $t0, 10                      # int1 = 10
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # a = int1
    sw $t0, -4($fp)
    li $t0, 3                       # int2 = 3
    sw $t0, -16($fp)
    lw $t0, -16($fp)                # b = int2
    sw $t0, -12($fp)
    li $t0, 1                       # bool3 = true
    sw $t0, -24($fp)
    lw $t0, -24($fp)                # activo = bool3
    sw $t0, -20($fp)
    lw $t0, -4($fp)                 # int4 = a
    sw $t0, -28($fp)
    lw $t0, -12($fp)                # int5 = b
    sw $t0, -32($fp)
    lw $t0, -28($fp)                # bool6 = int4 > int5
    lw $t1, -32($fp)
    sgt $t2, $t0, $t1
    sw $t2, -36($fp)
    lw $t0, -36($fp)                # if bool6 goto principal$if$1$bloque1
    bnez $t0, principal.if.1.bloque1
    j principal.if.1.siguiente1     # goto principal$if$1$siguiente1

principal.if.1.bloque1:
    li $t0, 1                       # int7 = 1
    sw $t0, -40($fp)
    lw $a0, -40($fp)                # write int7
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.if.1.final          # goto principal$if$1$final

principal.if.1.siguiente1:
    li $t0, 0                       # int8 = 0
    sw $t0, -44($fp)
    lw $a0, -44($fp)                # write int8
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.1.final:
    lw $t0, -4($fp)                 # int9 = a
    sw $t0, -48($fp)
    li $t0, 10                      # int10 = 10
    sw $t0, -52($fp)
    lw $t0, -48($fp)                # bool11 = int9 == int10
    lw $t1, -52($fp)
    seq $t2, $t0, $t1
    sw $t2, -56($fp)
    lw $t0, -12($fp)                # int12 = b
    sw $t0, -60($fp)
    li $t0, 3                       # int13 = 3
    sw $t0, -64($fp)
    lw $t0, -60($fp)                # bool14 = int12 != int13
    lw $t1, -64($fp)
    sne $t2, $t0, $t1
    sw $t2, -68($fp)
    lw $t0, -56($fp)                # bool15 = bool11 and bool14
    lw $t1, -68($fp)
    and $t2, $t0, $t1
    sw $t2, -72($fp)
    lw $t0, -72($fp)                # if bool15 goto principal$if$2$bloque1
    bnez $t0, principal.if.2.bloque1
    j principal.if.2.siguiente1     # goto principal$if$2$siguiente1

principal.if.2.bloque1:
    li $t0, 1                       # int16 = 1
    sw $t0, -76($fp)
    lw $a0, -76($fp)                # write int16
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.if.2.final          # goto principal$if$2$final

principal.if.2.siguiente1:
    li $t0, 2                       # int17 = 2
    sw $t0, -80($fp)
    lw $a0, -80($fp)                # write int17
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.2.final:
    lw $t0, -4($fp)                 # int18 = a
    sw $t0, -84($fp)
    li $t0, 5                       # int19 = 5
    sw $t0, -88($fp)
    lw $t0, -84($fp)                # bool20 = int18 < int19
    lw $t1, -88($fp)
    slt $t2, $t0, $t1
    sw $t2, -92($fp)
    lw $t0, -92($fp)                # if bool20 goto principal$if$3$bloque1
    bnez $t0, principal.if.3.bloque1
    j principal.if.3.siguiente1     # goto principal$if$3$siguiente1

principal.if.3.bloque1:
    li $t0, 1                       # int21 = 1
    sw $t0, -96($fp)
    lw $a0, -96($fp)                # write int21
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.if.3.final          # goto principal$if$3$final

principal.if.3.siguiente1:
    lw $t0, -4($fp)                 # int22 = a
    sw $t0, -100($fp)
    li $t0, 10                      # int23 = 10
    sw $t0, -104($fp)
    lw $t0, -100($fp)               # bool24 = int22 <= int23
    lw $t1, -104($fp)
    sle $t2, $t0, $t1
    sw $t2, -108($fp)
    lw $t0, -108($fp)               # if bool24 goto principal$if$3$bloque2
    bnez $t0, principal.if.3.bloque2
    j principal.if.3.siguiente2     # goto principal$if$3$siguiente2

principal.if.3.bloque2:
    li $t0, 2                       # int25 = 2
    sw $t0, -112($fp)
    lw $a0, -112($fp)               # write int25
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.if.3.final          # goto principal$if$3$final

principal.if.3.siguiente2:
    li $t0, 3                       # int26 = 3
    sw $t0, -116($fp)
    lw $a0, -116($fp)               # write int26
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.3.final:
    lw $t0, -4($fp)                 # int27 = a
    sw $t0, -120($fp)
    lw $t0, -12($fp)                # int28 = b
    sw $t0, -124($fp)
    lw $t0, -120($fp)               # bool29 = int27 < int28
    lw $t1, -124($fp)
    slt $t2, $t0, $t1
    sw $t2, -128($fp)
    lw $t0, -128($fp)               # bool30 = not bool29
    xori $t0, $t0, 1
    sw $t0, -132($fp)
    lw $t0, -132($fp)               # if bool30 goto principal$if$4$bloque1
    bnez $t0, principal.if.4.bloque1
    j principal.if.4.siguiente1     # goto principal$if$4$siguiente1

principal.if.4.bloque1:
    li $t0, 4                       # int31 = 4
    sw $t0, -136($fp)
    lw $a0, -136($fp)               # write int31
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.4.siguiente1:

principal.if.4.final:
    lw $t0, -4($fp)                 # int32 = a
    sw $t0, -140($fp)
    lw $t0, -12($fp)                # int33 = b
    sw $t0, -144($fp)
    lw $t0, -140($fp)               # bool34 = int32 < int33
    lw $t1, -144($fp)
    slt $t2, $t0, $t1
    sw $t2, -148($fp)
    lw $t0, -20($fp)                # bool35 = activo
    sw $t0, -152($fp)
    lw $t0, -148($fp)               # bool36 = bool34 or bool35
    lw $t1, -152($fp)
    or $t2, $t0, $t1
    sw $t2, -156($fp)
    lw $t0, -156($fp)               # if bool36 goto principal$if$5$bloque1
    bnez $t0, principal.if.5.bloque1
    j principal.if.5.siguiente1     # goto principal$if$5$siguiente1

principal.if.5.bloque1:
    li $t0, 5                       # int37 = 5
    sw $t0, -160($fp)
    lw $a0, -160($fp)               # write int37
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.5.siguiente1:

principal.if.5.final:
    li $t0, 3                       # int38 = 3
    sw $t0, -168($fp)
    lw $t0, -168($fp)               # c = int38
    sw $t0, -164($fp)

principal.while.6.condicion:
    lw $t0, -164($fp)               # int39 = c
    sw $t0, -172($fp)
    li $t0, 0                       # int40 = 0
    sw $t0, -176($fp)
    lw $t0, -172($fp)               # bool41 = int39 > int40
    lw $t1, -176($fp)
    sgt $t2, $t0, $t1
    sw $t2, -180($fp)
    lw $t0, -180($fp)               # if bool41 goto principal$while$6$bloque
    bnez $t0, principal.while.6.bloque
    j principal.while.6.final       # goto principal$while$6$final

principal.while.6.bloque:
    lw $t0, -164($fp)               # int42 = c
    sw $t0, -184($fp)
    lw $a0, -184($fp)               # write int42
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -164($fp)               # int43 = c
    sw $t0, -188($fp)
    li $t0, 1                       # int44 = 1
    sw $t0, -192($fp)
    lw $t0, -188($fp)               # int45 = int43 - int44
    lw $t1, -192($fp)
    sub $t2, $t0, $t1
    sw $t2, -196($fp)
    lw $t0, -196($fp)               # c = int45
    sw $t0, -164($fp)
    j principal.while.6.condicion   # goto principal$while$6$condicion

principal.while.6.final:

principal.for.7.inicializacion:
    li $t0, 0                       # int46 = 0
    sw $t0, -204($fp)
    lw $t0, -204($fp)               # i = int46
    sw $t0, -200($fp)

principal.for.7.condicion:
    lw $t0, -200($fp)               # int47 = i
    sw $t0, -208($fp)
    li $t0, 10                      # int48 = 10
    sw $t0, -212($fp)
    lw $t0, -208($fp)               # bool49 = int47 < int48
    lw $t1, -212($fp)
    slt $t2, $t0, $t1
    sw $t2, -216($fp)
    lw $t0, -216($fp)               # if bool49 goto principal$for$7$bloque
    bnez $t0, principal.for.7.bloque
    j principal.for.7.final         # goto principal$for$7$final

principal.for.7.incremento:
    lw $t0, -200($fp)               # int50 = i
    sw $t0, -220($fp)
    lw $t0, -220($fp)               # int51 = int50 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -224($fp)
    lw $t0, -224($fp)               # i = int51
    sw $t0, -200($fp)
    j principal.for.7.condicion     # goto principal$for$7$condicion

principal.for.7.bloque:
    lw $t0, -200($fp)               # int52 = i
    sw $t0, -228($fp)
    li $t0, 3                       # int53 = 3
    sw $t0, -232($fp)
    lw $t0, -228($fp)               # bool54 = int52 == int53
    lw $t1, -232($fp)
    seq $t2, $t0, $t1
    sw $t2, -236($fp)
    lw $t0, -236($fp)               # if bool54 goto principal$if$8$bloque1
    bnez $t0, principal.if.8.bloque1
    j principal.if.8.siguiente1     # goto principal$if$8$siguiente1

principal.if.8.bloque1:
    j principal.for.7.final         # goto principal$for$7$final

principal.if.8.siguiente1:

principal.if.8.final:
    lw $t0, -200($fp)               # int55 = i
    sw $t0, -240($fp)
    lw $a0, -240($fp)               # write int55
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.for.7.incremento    # goto principal$for$7$incremento

principal.for.7.final:
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
