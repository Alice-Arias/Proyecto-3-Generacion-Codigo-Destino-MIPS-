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
    addiu $sp, $sp, -56
    li $t0, 1                       # int1 = 1
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # a = int1
    sw $t0, -4($fp)
    li $t0, 2                       # int2 = 2
    sw $t0, -16($fp)
    lw $t0, -16($fp)                # b = int2
    sw $t0, -12($fp)
    li $t0, 0                       # int3 = 0
    sw $t0, -24($fp)
    lw $t0, -24($fp)                # c = int3
    sw $t0, -20($fp)
    lw $t0, -4($fp)                 # int4 = a
    sw $t0, -28($fp)
    lw $t0, -12($fp)                # int5 = b
    sw $t0, -32($fp)
    lw $t0, -28($fp)                # int6 = int4 + int5
    lw $t1, -32($fp)
    add $t2, $t0, $t1
    sw $t2, -36($fp)
    li $t0, 2                       # int7 = 2
    sw $t0, -40($fp)
    lw $t0, -36($fp)                # int8 = int6 * int7
    lw $t1, -40($fp)
    mul $t2, $t0, $t1
    sw $t2, -44($fp)
    lw $t0, -44($fp)                # c = int8
    sw $t0, -20($fp)
    lw $t0, -4($fp)                 # int9 = a
    sw $t0, -48($fp)
    lw $t0, -12($fp)                # int10 = b
    sw $t0, -52($fp)
    lw $t0, -48($fp)                # int11 = int9 mod int10
    lw $t1, -52($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -56($fp)
    lw $t0, -56($fp)                # c = int11
    sw $t0, -20($fp)
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
