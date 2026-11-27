.data

# ---------- numeros float fijos ----------
num_1: .float 2.5

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
    addiu $sp, $sp, -68
    li $t0, 0                       # int1 = 0
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # v1 = int1
    sw $t0, -4($fp)

principal.while.1.condicion:
    lw $t0, -4($fp)                 # int2 = v1
    sw $t0, -12($fp)
    li $t0, 2                       # int3 = 2
    sw $t0, -16($fp)
    lw $t0, -12($fp)                # bool4 = int2 < int3
    lw $t1, -16($fp)
    slt $t2, $t0, $t1
    sw $t2, -20($fp)
    lw $t0, -20($fp)                # if bool4 goto principal$while$1$bloque
    bnez $t0, principal.while.1.bloque
    j principal.while.1.final       # goto principal$while$1$final

principal.while.1.bloque:
    lw $t0, -4($fp)                 # int5 = v1
    sw $t0, -24($fp)
    li $t0, 0                       # int6 = 0
    sw $t0, -28($fp)
    lw $t0, -24($fp)                # bool7 = int5 == int6
    lw $t1, -28($fp)
    seq $t2, $t0, $t1
    sw $t2, -32($fp)
    lw $t0, -32($fp)                # if bool7 goto principal$if$2$bloque1
    bnez $t0, principal.if.2.bloque1
    j principal.if.2.siguiente1     # goto principal$if$2$siguiente1

principal.if.2.bloque1:
    li $t0, 10                      # int8 = 10
    sw $t0, -40($fp)
    lw $t0, -40($fp)                # v2 = int8
    sw $t0, -36($fp)
    lw $t0, -36($fp)                # int9 = v2
    sw $t0, -44($fp)
    lw $a0, -44($fp)                # write int9
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j principal.if.2.final          # goto principal$if$2$final

principal.if.2.siguiente1:
    l.s $f0, num_1                  # float10 = 2.5
    s.s $f0, -52($fp)
    l.s $f0, -52($fp)               # v3 = float10
    s.s $f0, -48($fp)
    l.s $f0, -48($fp)               # float11 = v3
    s.s $f0, -56($fp)
    l.s $f12, -56($fp)              # write float11
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.2.final:
    lw $t0, -4($fp)                 # int12 = v1
    sw $t0, -60($fp)
    li $t0, 1                       # int13 = 1
    sw $t0, -64($fp)
    lw $t0, -60($fp)                # int14 = int12 + int13
    lw $t1, -64($fp)
    add $t2, $t0, $t1
    sw $t2, -68($fp)
    lw $t0, -68($fp)                # v1 = int14
    sw $t0, -4($fp)
    j principal.while.1.condicion   # goto principal$while$1$condicion

principal.while.1.final:
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
