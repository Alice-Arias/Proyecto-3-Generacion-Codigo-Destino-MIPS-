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
    addiu $sp, $sp, -140
    li $t0, 10                      # int1 = 10
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # a = int1
    sw $t0, -4($fp)
    li $t0, 3                       # int2 = 3
    sw $t0, -16($fp)
    lw $t0, -16($fp)                # b = int2
    sw $t0, -12($fp)
    sw $zero, -20($fp)              # declaracion# int r
    lw $t0, -4($fp)                 # int3 = a
    sw $t0, -24($fp)
    lw $t0, -12($fp)                # int4 = b
    sw $t0, -28($fp)
    lw $t0, -24($fp)                # int5 = int3 + int4
    lw $t1, -28($fp)
    add $t2, $t0, $t1
    sw $t2, -32($fp)
    lw $t0, -32($fp)                # r = int5
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # int6 = r
    sw $t0, -36($fp)
    lw $a0, -36($fp)                # write int6
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -4($fp)                 # int7 = a
    sw $t0, -40($fp)
    lw $t0, -12($fp)                # int8 = b
    sw $t0, -44($fp)
    lw $t0, -40($fp)                # int9 = int7 - int8
    lw $t1, -44($fp)
    sub $t2, $t0, $t1
    sw $t2, -48($fp)
    lw $t0, -48($fp)                # r = int9
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # int10 = r
    sw $t0, -52($fp)
    lw $a0, -52($fp)                # write int10
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -4($fp)                 # int11 = a
    sw $t0, -56($fp)
    lw $t0, -12($fp)                # int12 = b
    sw $t0, -60($fp)
    lw $t0, -56($fp)                # int13 = int11 * int12
    lw $t1, -60($fp)
    mul $t2, $t0, $t1
    sw $t2, -64($fp)
    lw $t0, -64($fp)                # r = int13
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # int14 = r
    sw $t0, -68($fp)
    lw $a0, -68($fp)                # write int14
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -4($fp)                 # int15 = a
    sw $t0, -72($fp)
    lw $t0, -12($fp)                # int16 = b
    sw $t0, -76($fp)
    lw $t0, -72($fp)                # int17 = int15 // int16
    lw $t1, -76($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -80($fp)
    lw $t0, -80($fp)                # r = int17
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # int18 = r
    sw $t0, -84($fp)
    lw $a0, -84($fp)                # write int18
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -4($fp)                 # int19 = a
    sw $t0, -88($fp)
    lw $t0, -12($fp)                # int20 = b
    sw $t0, -92($fp)
    lw $t0, -88($fp)                # int21 = int19 mod int20
    lw $t1, -92($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -96($fp)
    lw $t0, -96($fp)                # r = int21
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # int22 = r
    sw $t0, -100($fp)
    lw $a0, -100($fp)               # write int22
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 2                       # int23 = 2
    sw $t0, -104($fp)
    li $t0, 5                       # int24 = 5
    sw $t0, -108($fp)
    lw $t0, -104($fp)               # int25 = int23 pot int24
    lw $t1, -108($fp)
    li $t2, 1

pot_ciclo_1:
    blez $t1, pot_fin_1
    mul $t2, $t2, $t0
    addi $t1, $t1, -1
    j pot_ciclo_1

pot_fin_1:
    sw $t2, -112($fp)
    lw $t0, -112($fp)               # r = int25
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # int26 = r
    sw $t0, -116($fp)
    lw $a0, -116($fp)               # write int26
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -4($fp)                 # int27 = a
    sw $t0, -120($fp)
    lw $t0, -12($fp)                # int28 = b
    sw $t0, -124($fp)
    lw $t0, -120($fp)               # int29 = int27 + int28
    lw $t1, -124($fp)
    add $t2, $t0, $t1
    sw $t2, -128($fp)
    li $t0, 2                       # int30 = 2
    sw $t0, -132($fp)
    lw $t0, -128($fp)               # int31 = int29 * int30
    lw $t1, -132($fp)
    mul $t2, $t0, $t1
    sw $t2, -136($fp)
    lw $t0, -136($fp)               # r = int31
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # int32 = r
    sw $t0, -140($fp)
    lw $a0, -140($fp)               # write int32
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
