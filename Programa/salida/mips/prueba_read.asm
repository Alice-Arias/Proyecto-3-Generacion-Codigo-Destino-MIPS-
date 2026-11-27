.data

# ---------- textos fijos ----------
txt_1: .asciiz "Ingrese la primera edad: "
txt_2: .asciiz "Ingrese la segunda edad: "
txt_3: .asciiz "Ingrese la tercera edad: "
txt_4: .asciiz "Ingrese la cuarta edad: "
txt_5: .asciiz "Ingrese la quinta edad: "
txt_6: .asciiz "Funciona el not"
txt_7: .asciiz "\nEdades seleccionadas: "

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
    addiu $sp, $sp, -248
    sw $zero, -4($fp)               # declaracion# int v1
    li $t0, 0                       # int1 = 0
    sw $t0, -28($fp)
    li $t0, 0                       # int2 = 0
    sw $t0, -32($fp)
    li $t0, 0                       # int3 = 0
    sw $t0, -36($fp)
    li $t0, 0                       # int4 = 0
    sw $t0, -40($fp)
    li $t0, 0                       # int5 = 0
    sw $t0, -44($fp)
    lw $t0, -28($fp)                # v2[0] = int1
    addiu $t9, $fp, -24
    sw $t0, 0($t9)
    lw $t0, -32($fp)                # v2[1] = int2
    addiu $t9, $fp, -24
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    lw $t0, -36($fp)                # v2[2] = int3
    addiu $t9, $fp, -24
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    lw $t0, -40($fp)                # v2[3] = int4
    addiu $t9, $fp, -24
    addiu $t9, $t9, 12
    sw $t0, 0($t9)
    lw $t0, -44($fp)                # v2[4] = int5
    addiu $t9, $fp, -24
    addiu $t9, $t9, 16
    sw $t0, 0($t9)
    li $t0, 1                       # int6 = 1
    sw $t0, -52($fp)
    lw $t0, -52($fp)                # v3 = int6
    sw $t0, -48($fp)
    li $t0, 5                       # int7 = 5
    sw $t0, -60($fp)
    lw $t0, -60($fp)                # v4 = int7
    sw $t0, -56($fp)
    li $t0, 2                       # int8 = 2
    sw $t0, -68($fp)
    lw $t0, -68($fp)                # v5 = int8
    sw $t0, -64($fp)
    la $t0, txt_1                   # string9 = "Ingrese la primera edad: "
    sw $t0, -72($fp)
    lw $a0, -72($fp)                # write string9
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read v1
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int10 = v1
    sw $t0, -76($fp)
    lw $t0, -76($fp)                # v2[0] = int10
    addiu $t9, $fp, -24
    sw $t0, 0($t9)
    la $t0, txt_2                   # string11 = "Ingrese la segunda edad: "
    sw $t0, -80($fp)
    lw $a0, -80($fp)                # write string11
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read v1
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int12 = v1
    sw $t0, -84($fp)
    lw $t0, -84($fp)                # v2[1] = int12
    addiu $t9, $fp, -24
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    la $t0, txt_3                   # string13 = "Ingrese la tercera edad: "
    sw $t0, -88($fp)
    lw $a0, -88($fp)                # write string13
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read v1
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int14 = v1
    sw $t0, -92($fp)
    lw $t0, -92($fp)                # v2[2] = int14
    addiu $t9, $fp, -24
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    la $t0, txt_4                   # string15 = "Ingrese la cuarta edad: "
    sw $t0, -96($fp)
    lw $a0, -96($fp)                # write string15
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read v1
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int16 = v1
    sw $t0, -100($fp)
    lw $t0, -100($fp)               # v2[3] = int16
    addiu $t9, $fp, -24
    addiu $t9, $t9, 12
    sw $t0, 0($t9)
    la $t0, txt_5                   # string17 = "Ingrese la quinta edad: "
    sw $t0, -104($fp)
    lw $a0, -104($fp)               # write string17
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read v1
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int18 = v1
    sw $t0, -108($fp)
    lw $t0, -108($fp)               # v2[4] = int18
    addiu $t9, $fp, -24
    addiu $t9, $t9, 16
    sw $t0, 0($t9)
    lw $t0, -48($fp)                # int19 = v3
    sw $t0, -116($fp)
    lw $t0, -116($fp)               # int20 = int19 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -120($fp)
    lw $t0, -120($fp)               # v3 = int20
    sw $t0, -48($fp)
    li $t0, -14                     # int21 = -14
    sw $t0, -124($fp)
    lw $t0, -116($fp)               # int22 = int19 - int21
    lw $t1, -124($fp)
    sub $t2, $t0, $t1
    sw $t2, -128($fp)
    li $t0, 5                       # int23 = 5
    sw $t0, -132($fp)
    li $t0, 7                       # int24 = 7
    sw $t0, -136($fp)
    lw $t0, -132($fp)               # int25 = int23 + int24
    lw $t1, -136($fp)
    add $t2, $t0, $t1
    sw $t2, -140($fp)
    li $t0, 15                      # int26 = 15
    sw $t0, -144($fp)
    lw $t0, -140($fp)               # int27 = int25 // int26
    lw $t1, -144($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -148($fp)
    li $t0, 10                      # int28 = 10
    sw $t0, -152($fp)
    li $t0, -3050                   # int29 = -3050 pot int28
    lw $t1, -152($fp)
    li $t2, 1

pot_ciclo_1:
    blez $t1, pot_fin_1
    mul $t2, $t2, $t0
    addi $t1, $t1, -1
    j pot_ciclo_1

pot_fin_1:
    sw $t2, -156($fp)
    lw $t0, -148($fp)               # int30 = int27 * int29
    lw $t1, -156($fp)
    mul $t2, $t0, $t1
    sw $t2, -160($fp)
    li $t0, 3                       # int31 = 3
    sw $t0, -164($fp)
    lw $t0, -160($fp)               # int32 = int30 // int31
    lw $t1, -164($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -168($fp)
    lw $t0, -128($fp)               # int33 = int22 mod int32
    lw $t1, -168($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -172($fp)
    lw $t0, -172($fp)               # v6 = int33
    sw $t0, -112($fp)
    lw $t0, -112($fp)               # int34 = v6
    sw $t0, -176($fp)
    lw $a0, -176($fp)               # write int34
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -56($fp)                # int35 = v4
    sw $t0, -180($fp)
    lw $t0, -64($fp)                # int36 = v5
    sw $t0, -184($fp)
    lw $t0, -180($fp)               # bool37 = int35 < int36
    lw $t1, -184($fp)
    slt $t2, $t0, $t1
    sw $t2, -188($fp)
    lw $t0, -188($fp)               # bool38 = not bool37
    xori $t0, $t0, 1
    sw $t0, -192($fp)
    lw $t0, -192($fp)               # if bool38 goto principal$if$1$bloque1
    bnez $t0, principal.if.1.bloque1
    j principal.if.1.siguiente1     # goto principal$if$1$siguiente1

principal.if.1.bloque1:
    la $t0, txt_6                   # string39 = "Funciona el not"
    sw $t0, -196($fp)
    lw $a0, -196($fp)               # write string39
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.1.siguiente1:

principal.if.1.final:
    li $t0, -142                    # int40 = -142
    sw $t0, -204($fp)
    li $t0, 52                      # int41 = 52
    sw $t0, -208($fp)
    lw $t0, -204($fp)               # int42 = int40 mod int41
    lw $t1, -208($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -212($fp)
    lw $t0, -212($fp)               # v7 = int42
    sw $t0, -200($fp)
    la $t0, txt_7                   # string43 = "\nEdades seleccionadas: "
    sw $t0, -216($fp)
    lw $a0, -216($fp)               # write string43
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.for.2.inicializacion:
    li $t0, 0                       # int44 = 0
    sw $t0, -224($fp)
    lw $t0, -224($fp)               # v8 = int44
    sw $t0, -220($fp)

principal.for.2.condicion:
    lw $t0, -220($fp)               # int45 = v8
    sw $t0, -228($fp)
    li $t0, 5                       # int46 = 5
    sw $t0, -232($fp)
    lw $t0, -228($fp)               # bool47 = int45 < int46
    lw $t1, -232($fp)
    slt $t2, $t0, $t1
    sw $t2, -236($fp)
    lw $t0, -236($fp)               # if bool47 goto principal$for$2$bloque
    bnez $t0, principal.for.2.bloque
    j principal.for.2.final         # goto principal$for$2$final

principal.for.2.incremento:
    lw $t0, -220($fp)               # int48 = v8
    sw $t0, -240($fp)
    lw $t0, -240($fp)               # int49 = int48 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -244($fp)
    lw $t0, -244($fp)               # v8 = int49
    sw $t0, -220($fp)
    j principal.for.2.condicion     # goto principal$for$2$condicion

principal.for.2.bloque:
    addiu $t9, $fp, -24             # int50 = v2[v8]
    lw $t8, -220($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -248($fp)
    lw $a0, -248($fp)               # write int50
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.for.2.incremento    # goto principal$for$2$incremento

principal.for.2.final:
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
