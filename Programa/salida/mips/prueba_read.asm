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
    addiu $sp, $sp, -184
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
    la $t0, txt_1                   # string6 = "Ingrese la primera edad: "
    sw $t0, -48($fp)
    lw $a0, -48($fp)                # write string6
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int7 = edad
    sw $t0, -52($fp)
    lw $t0, -52($fp)                # edades[0] = int7
    addiu $t9, $fp, -24
    sw $t0, 0($t9)
    la $t0, txt_2                   # string8 = "Ingrese la segunda edad: "
    sw $t0, -56($fp)
    lw $a0, -56($fp)                # write string8
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int9 = edad
    sw $t0, -60($fp)
    lw $t0, -60($fp)                # edades[1] = int9
    addiu $t9, $fp, -24
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    la $t0, txt_3                   # string10 = "Ingrese la tercera edad: "
    sw $t0, -64($fp)
    lw $a0, -64($fp)                # write string10
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int11 = edad
    sw $t0, -68($fp)
    lw $t0, -68($fp)                # edades[2] = int11
    addiu $t9, $fp, -24
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    la $t0, txt_4                   # string12 = "Ingrese la cuarta edad: "
    sw $t0, -72($fp)
    lw $a0, -72($fp)                # write string12
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int13 = edad
    sw $t0, -76($fp)
    lw $t0, -76($fp)                # edades[3] = int13
    addiu $t9, $fp, -24
    addiu $t9, $t9, 12
    sw $t0, 0($t9)
    la $t0, txt_5                   # string14 = "Ingrese la quinta edad: "
    sw $t0, -80($fp)
    lw $a0, -80($fp)                # write string14
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    lw $t0, -4($fp)                 # int15 = edad
    sw $t0, -84($fp)
    lw $t0, -84($fp)                # edades[4] = int15
    addiu $t9, $fp, -24
    addiu $t9, $t9, 16
    sw $t0, 0($t9)
    li $t0, 1                       # int16 = 1
    sw $t0, -92($fp)
    li $t0, -14                     # int17 = -14
    sw $t0, -96($fp)
    lw $t0, -92($fp)                # int18 = int16 - int17
    lw $t1, -96($fp)
    sub $t2, $t0, $t1
    sw $t2, -100($fp)
    li $t0, 5                       # int19 = 5
    sw $t0, -104($fp)
    li $t0, 7                       # int20 = 7
    sw $t0, -108($fp)
    lw $t0, -104($fp)               # int21 = int19 + int20
    lw $t1, -108($fp)
    add $t2, $t0, $t1
    sw $t2, -112($fp)
    li $t0, 15                      # int22 = 15
    sw $t0, -116($fp)
    lw $t0, -112($fp)               # int23 = int21 // int22
    lw $t1, -116($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -120($fp)
    li $t0, 10                      # int24 = 10
    sw $t0, -124($fp)
    li $t0, -3050                   # int25 = -3050 pot int24
    lw $t1, -124($fp)
    li $t2, 1

pot_ciclo_1:
    blez $t1, pot_fin_1
    mul $t2, $t2, $t0
    addi $t1, $t1, -1
    j pot_ciclo_1

pot_fin_1:
    sw $t2, -128($fp)
    lw $t0, -120($fp)               # int26 = int23 * int25
    lw $t1, -128($fp)
    mul $t2, $t0, $t1
    sw $t2, -132($fp)
    li $t0, 3                       # int27 = 3
    sw $t0, -136($fp)
    lw $t0, -132($fp)               # int28 = int26 // int27
    lw $t1, -136($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -140($fp)
    lw $t0, -100($fp)               # int29 = int18 mod int28
    lw $t1, -140($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -144($fp)
    lw $t0, -144($fp)               # in1 = int29
    sw $t0, -88($fp)
    lw $t0, -88($fp)                # int30 = in1
    sw $t0, -148($fp)
    lw $a0, -148($fp)               # write int30
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_6                   # string31 = "\nEdades seleccionadas: "
    sw $t0, -152($fp)
    lw $a0, -152($fp)               # write string31
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.for.1.inicializacion:
    li $t0, 0                       # int32 = 0
    sw $t0, -160($fp)
    lw $t0, -160($fp)               # i = int32
    sw $t0, -156($fp)

principal.for.1.condicion:
    lw $t0, -156($fp)               # int33 = i
    sw $t0, -164($fp)
    li $t0, 5                       # int34 = 5
    sw $t0, -168($fp)
    lw $t0, -164($fp)               # bool35 = int33 < int34
    lw $t1, -168($fp)
    slt $t2, $t0, $t1
    sw $t2, -172($fp)
    lw $t0, -172($fp)               # if bool35 goto principal$for$1$bloque
    bnez $t0, principal.for.1.bloque
    j principal.for.1.final         # goto principal$for$1$final

principal.for.1.incremento:
    lw $t0, -156($fp)               # int36 = i
    sw $t0, -176($fp)
    lw $t0, -176($fp)               # int37 = int36 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -180($fp)
    lw $t0, -180($fp)               # i = int37
    sw $t0, -156($fp)
    j principal.for.1.condicion     # goto principal$for$1$condicion

principal.for.1.bloque:
    addiu $t9, $fp, -24             # int38 = edades[i]
    lw $t8, -156($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -184($fp)
    lw $a0, -184($fp)               # write int38
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
