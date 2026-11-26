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
    addiu $sp, $sp, -120
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
    la $t0, txt_6                   # string16 = "\nEdades seleccionadas: "
    sw $t0, -88($fp)
    lw $a0, -88($fp)                # write string16
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.for.1.inicializacion:
    li $t0, 0                       # int17 = 0
    sw $t0, -96($fp)
    lw $t0, -96($fp)                # i = int17
    sw $t0, -92($fp)

principal.for.1.condicion:
    lw $t0, -92($fp)                # int18 = i
    sw $t0, -100($fp)
    li $t0, 5                       # int19 = 5
    sw $t0, -104($fp)
    lw $t0, -100($fp)               # bool20 = int18 < int19
    lw $t1, -104($fp)
    slt $t2, $t0, $t1
    sw $t2, -108($fp)
    lw $t0, -108($fp)               # if bool20 goto principal$for$1$bloque
    bnez $t0, principal.for.1.bloque
    j principal.for.1.final         # goto principal$for$1$final

principal.for.1.incremento:
    lw $t0, -92($fp)                # int21 = i
    sw $t0, -112($fp)
    lw $t0, -112($fp)               # int22 = int21 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -116($fp)
    lw $t0, -116($fp)               # i = int22
    sw $t0, -92($fp)
    j principal.for.1.condicion     # goto principal$for$1$condicion

principal.for.1.bloque:
    addiu $t9, $fp, -24             # int23 = edades[i]
    lw $t8, -92($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -120($fp)
    lw $a0, -120($fp)               # write int23
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
