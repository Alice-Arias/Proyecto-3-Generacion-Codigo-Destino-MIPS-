.data
g_int1: .word 0
g_a: .word 0
g_int2: .word 0
g_b: .word 0
g_int3: .word 0
g_int4: .word 0

.text
.globl main
main:
    # void$principal:
    # declaracionPrevia# int a
    # int1 = 5
    li $t0, 5
    sw $t0, g_int1
    # a = int1
    lw $t0, g_int1
    sw $t0, g_a
    # declaracionPrevia# int b
    # int2 = 7
    li $t0, 7
    sw $t0, g_int2
    # b = int2
    lw $t0, g_int2
    sw $t0, g_b
    # int3 = a
    lw $t0, g_a
    sw $t0, g_int3
    # write int3
    lw $a0, g_int3
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int4 = b
    lw $t0, g_b
    sw $t0, g_int4
    # write int4
    lw $a0, g_int4
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # return
    # void$principal$final
    li $v0, 10
    syscall
