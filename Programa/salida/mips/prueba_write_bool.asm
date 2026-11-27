.data

# ---------- textos fijos ----------
txt_1: .asciiz "true"
txt_2: .asciiz "false"

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
    addiu $sp, $sp, -32
    li $t0, 1                       # bool1 = true
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # v1 = bool1
    sw $t0, -4($fp)
    li $t0, 0                       # bool2 = false
    sw $t0, -16($fp)
    lw $t0, -16($fp)                # v2 = bool2
    sw $t0, -12($fp)
    lw $t0, -4($fp)                 # bool3 = v1
    sw $t0, -20($fp)
    lw $t0, -20($fp)                # write bool3
    beq $t0, $zero, write_bool_false_1
    la $a0, txt_1
    li $v0, 4
    syscall
    j write_bool_end_1

write_bool_false_1:
    la $a0, txt_2
    li $v0, 4
    syscall

write_bool_end_1:
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -12($fp)                # bool4 = v2
    sw $t0, -24($fp)
    lw $t0, -24($fp)                # write bool4
    beq $t0, $zero, write_bool_false_2
    la $a0, txt_1
    li $v0, 4
    syscall
    j write_bool_end_2

write_bool_false_2:
    la $a0, txt_2
    li $v0, 4
    syscall

write_bool_end_2:
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 1                       # bool5 = true
    sw $t0, -28($fp)
    lw $t0, -28($fp)                # write bool5
    beq $t0, $zero, write_bool_false_3
    la $a0, txt_1
    li $v0, 4
    syscall
    j write_bool_end_3

write_bool_false_3:
    la $a0, txt_2
    li $v0, 4
    syscall

write_bool_end_3:
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 0                       # bool6 = false
    sw $t0, -32($fp)
    lw $t0, -32($fp)                # write bool6
    beq $t0, $zero, write_bool_false_4
    la $a0, txt_1
    li $v0, 4
    syscall
    j write_bool_end_4

write_bool_false_4:
    la $a0, txt_2
    li $v0, 4
    syscall

write_bool_end_4:
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
