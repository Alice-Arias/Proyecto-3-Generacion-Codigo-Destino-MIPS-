.data
g_int1: .word 0
g_a: .word 0
g_int2: .word 0
g_b: .word 0
g_int3: .word 0
g_int4: .word 0
g_int5: .word 0
g_r: .word 0
g_int6: .word 0
g_int7: .word 0
g_int8: .word 0
g_int9: .word 0
g_int10: .word 0
g_int11: .word 0
g_int12: .word 0
g_int13: .word 0
g_int14: .word 0
g_int15: .word 0
g_int16: .word 0
g_int17: .word 0
g_int18: .word 0
g_int19: .word 0
g_int20: .word 0
g_int21: .word 0
g_int22: .word 0
g_int23: .word 0
g_int24: .word 0
g_int25: .word 0
g_int26: .word 0
g_int27: .word 0
g_int28: .word 0
g_int29: .word 0
g_int30: .word 0
g_int31: .word 0
g_int32: .word 0

.text
.globl main
main:
    # void$principal:
    # declaracionPrevia# int a
    # int1 = 10
    li $t0, 10
    sw $t0, g_int1
    # a = int1
    lw $t0, g_int1
    sw $t0, g_a
    # declaracionPrevia# int b
    # int2 = 3
    li $t0, 3
    sw $t0, g_int2
    # b = int2
    lw $t0, g_int2
    sw $t0, g_b
    # declaracion# int r
    # int3 = a
    lw $t0, g_a
    sw $t0, g_int3
    # int4 = b
    lw $t0, g_b
    sw $t0, g_int4
    # int5 = int3 + int4
    lw $t0, g_int3
    lw $t1, g_int4
    add $t2, $t0, $t1
    sw $t2, g_int5
    # r = int5
    lw $t0, g_int5
    sw $t0, g_r
    # int6 = r
    lw $t0, g_r
    sw $t0, g_int6
    # write int6
    lw $a0, g_int6
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int7 = a
    lw $t0, g_a
    sw $t0, g_int7
    # int8 = b
    lw $t0, g_b
    sw $t0, g_int8
    # int9 = int7 - int8
    lw $t0, g_int7
    lw $t1, g_int8
    sub $t2, $t0, $t1
    sw $t2, g_int9
    # r = int9
    lw $t0, g_int9
    sw $t0, g_r
    # int10 = r
    lw $t0, g_r
    sw $t0, g_int10
    # write int10
    lw $a0, g_int10
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int11 = a
    lw $t0, g_a
    sw $t0, g_int11
    # int12 = b
    lw $t0, g_b
    sw $t0, g_int12
    # int13 = int11 * int12
    lw $t0, g_int11
    lw $t1, g_int12
    mul $t2, $t0, $t1
    sw $t2, g_int13
    # r = int13
    lw $t0, g_int13
    sw $t0, g_r
    # int14 = r
    lw $t0, g_r
    sw $t0, g_int14
    # write int14
    lw $a0, g_int14
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int15 = a
    lw $t0, g_a
    sw $t0, g_int15
    # int16 = b
    lw $t0, g_b
    sw $t0, g_int16
    # int17 = int15 // int16
    lw $t0, g_int15
    lw $t1, g_int16
    div $t0, $t1
    mflo $t2
    sw $t2, g_int17
    # r = int17
    lw $t0, g_int17
    sw $t0, g_r
    # int18 = r
    lw $t0, g_r
    sw $t0, g_int18
    # write int18
    lw $a0, g_int18
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int19 = a
    lw $t0, g_a
    sw $t0, g_int19
    # int20 = b
    lw $t0, g_b
    sw $t0, g_int20
    # int21 = int19 mod int20
    lw $t0, g_int19
    lw $t1, g_int20
    div $t0, $t1
    mfhi $t2
    sw $t2, g_int21
    # r = int21
    lw $t0, g_int21
    sw $t0, g_r
    # int22 = r
    lw $t0, g_r
    sw $t0, g_int22
    # write int22
    lw $a0, g_int22
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int23 = 2
    li $t0, 2
    sw $t0, g_int23
    # int24 = 5
    li $t0, 5
    sw $t0, g_int24
    # int25 = int23 pot int24
    lw $t0, g_int23
    lw $t1, g_int24
    li $t2, 1
pot_ciclo_1:
    blez $t1, pot_fin_1
    mul $t2, $t2, $t0
    addi $t1, $t1, -1
    j pot_ciclo_1
pot_fin_1:
    sw $t2, g_int25
    # r = int25
    lw $t0, g_int25
    sw $t0, g_r
    # int26 = r
    lw $t0, g_r
    sw $t0, g_int26
    # write int26
    lw $a0, g_int26
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # int27 = a
    lw $t0, g_a
    sw $t0, g_int27
    # int28 = b
    lw $t0, g_b
    sw $t0, g_int28
    # int29 = int27 + int28
    lw $t0, g_int27
    lw $t1, g_int28
    add $t2, $t0, $t1
    sw $t2, g_int29
    # int30 = 2
    li $t0, 2
    sw $t0, g_int30
    # int31 = int29 * int30
    lw $t0, g_int29
    lw $t1, g_int30
    mul $t2, $t0, $t1
    sw $t2, g_int31
    # r = int31
    lw $t0, g_int31
    sw $t0, g_r
    # int32 = r
    lw $t0, g_r
    sw $t0, g_int32
    # write int32
    lw $a0, g_int32
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    # return
    # void$principal$final
    li $v0, 10
    syscall
