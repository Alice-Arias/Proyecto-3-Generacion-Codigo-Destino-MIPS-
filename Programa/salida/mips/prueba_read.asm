.data

# ---------- textos fijos ----------
txt_1: .asciiz "Ingrese su edad: "
txt_2: .asciiz "Ingrese su promedio: "
txt_3: .asciiz "\nEdad ingresada: "
txt_4: .asciiz "\nEdad en meses: "
txt_5: .asciiz "Promedio ingresado: "

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
    addiu $sp, $sp, -60
    sw $zero, -4($fp)               # declaracion# int edad
    sw $zero, -8($fp)               # declaracion# float promedio
    li $t0, 0                       # int1 = 0
    sw $t0, -16($fp)
    lw $t0, -16($fp)                # edadEnMeses = int1
    sw $t0, -12($fp)
    la $t0, txt_1                   # string2 = "Ingrese su edad: "
    sw $t0, -20($fp)
    lw $a0, -20($fp)                # write string2
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 5                       # read edad
    syscall
    sw $v0, -4($fp)
    la $t0, txt_2                   # string3 = "Ingrese su promedio: "
    sw $t0, -24($fp)
    lw $a0, -24($fp)                # write string3
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 6                       # read promedio
    syscall
    s.s $f0, -8($fp)
    lw $t0, -4($fp)                 # int4 = edad
    sw $t0, -28($fp)
    li $t0, 12                      # int5 = 12
    sw $t0, -32($fp)
    lw $t0, -28($fp)                # int6 = int4 * int5
    lw $t1, -32($fp)
    mul $t2, $t0, $t1
    sw $t2, -36($fp)
    lw $t0, -36($fp)                # edadEnMeses = int6
    sw $t0, -12($fp)
    la $t0, txt_3                   # string7 = "\nEdad ingresada: "
    sw $t0, -40($fp)
    lw $a0, -40($fp)                # write string7
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -4($fp)                 # int8 = edad
    sw $t0, -44($fp)
    lw $a0, -44($fp)                # write int8
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_4                   # string9 = "\nEdad en meses: "
    sw $t0, -48($fp)
    lw $a0, -48($fp)                # write string9
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -12($fp)                # int10 = edadEnMeses
    sw $t0, -52($fp)
    lw $a0, -52($fp)                # write int10
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_5                   # string11 = "Promedio ingresado: "
    sw $t0, -56($fp)
    lw $a0, -56($fp)                # write string11
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, -8($fp)                # float12 = promedio
    s.s $f0, -60($fp)
    l.s $f12, -60($fp)              # write float12
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
