.data

# ---------- textos fijos ----------
txt_1: .asciiz "Ingrese la primera edad: "
txt_2: .asciiz "Ingrese la segunda edad: "
txt_3: .asciiz "Ingrese la tercera edad: "
txt_4: .asciiz "Ingrese la cuarta edad: "
txt_5: .asciiz "Ingrese la quinta edad: "
txt_6: .asciiz "\nEdades seleccionadas: "

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
    addiu $sp, $sp, -196
    sw $zero, -4($fp)               # declaracion# int edad
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
    lw $t0, -28($fp)                # edades[0] = int1
    addiu $t9, $fp, -24
    sw $t0, 0($t9)
    lw $t0, -32($fp)                # edades[1] = int2
    addiu $t9, $fp, -24
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    lw $t0, -36($fp)                # edades[2] = int3
    addiu $t9, $fp, -24
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    lw $t0, -40($fp)                # edades[3] = int4
    addiu $t9, $fp, -24
    addiu $t9, $t9, 12
    sw $t0, 0($t9)
    lw $t0, -44($fp)                # edades[4] = int5
    addiu $t9, $fp, -24
    addiu $t9, $t9, 16
    sw $t0, 0($t9)
    li $t0, 1                       # int6 = 1
    sw $t0, -52($fp)
    lw $t0, -52($fp)                # i = int6
    sw $t0, -48($fp)
    la $t0, txt_1                   # string7 = "Ingrese la primera edad: "
    sw $t0, -56($fp)
    lw $a0, -56($fp)                # write string7
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int8 = edad
    sw $t0, -60($fp)
    lw $t0, -60($fp)                # edades[0] = int8
    addiu $t9, $fp, -24
    sw $t0, 0($t9)
    la $t0, txt_2                   # string9 = "Ingrese la segunda edad: "
    sw $t0, -64($fp)
    lw $a0, -64($fp)                # write string9
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int10 = edad
    sw $t0, -68($fp)
    lw $t0, -68($fp)                # edades[1] = int10
    addiu $t9, $fp, -24
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    la $t0, txt_3                   # string11 = "Ingrese la tercera edad: "
    sw $t0, -72($fp)
    lw $a0, -72($fp)                # write string11
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int12 = edad
    sw $t0, -76($fp)
    lw $t0, -76($fp)                # edades[2] = int12
    addiu $t9, $fp, -24
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    la $t0, txt_4                   # string13 = "Ingrese la cuarta edad: "
    sw $t0, -80($fp)
    lw $a0, -80($fp)                # write string13
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int14 = edad
    sw $t0, -84($fp)
    lw $t0, -84($fp)                # edades[3] = int14
    addiu $t9, $fp, -24
    addiu $t9, $t9, 12
    sw $t0, 0($t9)
    la $t0, txt_5                   # string15 = "Ingrese la quinta edad: "
    sw $t0, -88($fp)
    lw $a0, -88($fp)                # write string15
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int16 = edad
    sw $t0, -92($fp)
    lw $t0, -92($fp)                # edades[4] = int16
    addiu $t9, $fp, -24
    addiu $t9, $t9, 16
    sw $t0, 0($t9)
    lw $t0, -48($fp)                # int17 = i
    sw $t0, -100($fp)
    lw $t0, -100($fp)               # int18 = int17 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -104($fp)
    lw $t0, -104($fp)               # i = int18
    sw $t0, -48($fp)
    li $t0, -14                     # int19 = -14
    sw $t0, -108($fp)
    lw $t0, -100($fp)               # int20 = int17 - int19
    lw $t1, -108($fp)
    sub $t2, $t0, $t1
    sw $t2, -112($fp)
    li $t0, 5                       # int21 = 5
    sw $t0, -116($fp)
    li $t0, 7                       # int22 = 7
    sw $t0, -120($fp)
    lw $t0, -116($fp)               # int23 = int21 + int22
    lw $t1, -120($fp)
    add $t2, $t0, $t1
    sw $t2, -124($fp)
    li $t0, 15                      # int24 = 15
    sw $t0, -128($fp)
    lw $t0, -124($fp)               # int25 = int23 // int24
    lw $t1, -128($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -132($fp)
    li $t0, 10                      # int26 = 10
    sw $t0, -136($fp)
    li $t0, -3050                   # int27 = -3050 pot int26
    lw $t1, -136($fp)
    li $t2, 1

pot_ciclo_1:
    blez $t1, pot_fin_1
    mul $t2, $t2, $t0
    addi $t1, $t1, -1
    j pot_ciclo_1

pot_fin_1:
    sw $t2, -140($fp)
    lw $t0, -132($fp)               # int28 = int25 * int27
    lw $t1, -140($fp)
    mul $t2, $t0, $t1
    sw $t2, -144($fp)
    li $t0, 3                       # int29 = 3
    sw $t0, -148($fp)
    lw $t0, -144($fp)               # int30 = int28 // int29
    lw $t1, -148($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -152($fp)
    lw $t0, -112($fp)               # int31 = int20 mod int30
    lw $t1, -152($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -156($fp)
    lw $t0, -156($fp)               # in1 = int31
    sw $t0, -96($fp)
    lw $t0, -96($fp)                # int32 = in1
    sw $t0, -160($fp)
    lw $a0, -160($fp)               # write int32
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_6                   # string33 = "\nEdades seleccionadas: "
    sw $t0, -164($fp)
    lw $a0, -164($fp)               # write string33
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.for.1.inicializacion:
    li $t0, 0                       # int34 = 0
    sw $t0, -172($fp)
    lw $t0, -172($fp)               # y = int34
    sw $t0, -168($fp)

principal.for.1.condicion:
    lw $t0, -168($fp)               # int35 = y
    sw $t0, -176($fp)
    li $t0, 5                       # int36 = 5
    sw $t0, -180($fp)
    lw $t0, -176($fp)               # bool37 = int35 < int36
    lw $t1, -180($fp)
    slt $t2, $t0, $t1
    sw $t2, -184($fp)
    lw $t0, -184($fp)               # if bool37 goto principal$for$1$bloque
    bnez $t0, principal.for.1.bloque
    j principal.for.1.final         # goto principal$for$1$final

principal.for.1.incremento:
    lw $t0, -168($fp)               # int38 = y
    sw $t0, -188($fp)
    lw $t0, -188($fp)               # int39 = int38 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -192($fp)
    lw $t0, -192($fp)               # y = int39
    sw $t0, -168($fp)
    j principal.for.1.condicion     # goto principal$for$1$condicion

principal.for.1.bloque:
    addiu $t9, $fp, -24             # int40 = edades[y]
    lw $t8, -168($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -196($fp)
    lw $a0, -196($fp)               # write int40
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.for.1.incremento    # goto principal$for$1$incremento

principal.for.1.final:
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
