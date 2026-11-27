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
    addiu $sp, $sp, -24
    li $t0, 5                       # int1 = 5
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # a = int1
    sw $t0, -4($fp)
    li $t0, 7                       # int2 = 7
    sw $t0, -16($fp)
    lw $t0, -16($fp)                # b = int2
    sw $t0, -12($fp)
    lw $t0, -4($fp)                 # int3 = a
    sw $t0, -20($fp)
    lw $a0, -20($fp)                # write int3
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -12($fp)                # int4 = b
    sw $t0, -24($fp)
    lw $a0, -24($fp)                # write int4
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
