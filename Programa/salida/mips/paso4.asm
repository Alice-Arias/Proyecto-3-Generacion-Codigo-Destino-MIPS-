.data

# ---------- numeros float fijos ----------
num_1: .float 7.5
num_2: .float 2.0

# ---------- textos fijos ----------
txt_1: .asciiz "Hola MIPS"
txt_2: .asciiz "fin de textos"
txt_3: .asciiz "x es mayor"
txt_4: .asciiz "la letra es A"

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
    addiu $sp, $sp, -124
    l.s $f0, num_1                  # float1 = 7.5
    s.s $f0, -8($fp)
    l.s $f0, -8($fp)                # x = float1
    s.s $f0, -4($fp)
    l.s $f0, num_2                  # float2 = 2.0
    s.s $f0, -16($fp)
    l.s $f0, -16($fp)               # y = float2
    s.s $f0, -12($fp)
    sw $zero, -20($fp)              # declaracion# float z
    l.s $f0, -4($fp)                # float3 = x
    s.s $f0, -24($fp)
    l.s $f0, -12($fp)               # float4 = y
    s.s $f0, -28($fp)
    l.s $f0, -24($fp)               # float5 = float3 + float4
    l.s $f2, -28($fp)
    add.s $f4, $f0, $f2
    s.s $f4, -32($fp)
    l.s $f0, -32($fp)               # z = float5
    s.s $f0, -20($fp)
    l.s $f0, -20($fp)               # float6 = z
    s.s $f0, -36($fp)
    l.s $f12, -36($fp)              # write float6
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, -4($fp)                # float7 = x
    s.s $f0, -40($fp)
    l.s $f0, -12($fp)               # float8 = y
    s.s $f0, -44($fp)
    l.s $f0, -40($fp)               # float9 = float7 * float8
    l.s $f2, -44($fp)
    mul.s $f4, $f0, $f2
    s.s $f4, -48($fp)
    l.s $f0, -48($fp)               # z = float9
    s.s $f0, -20($fp)
    l.s $f0, -20($fp)               # float10 = z
    s.s $f0, -52($fp)
    l.s $f12, -52($fp)              # write float10
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, -4($fp)                # float11 = x
    s.s $f0, -56($fp)
    l.s $f0, -12($fp)               # float12 = y
    s.s $f0, -60($fp)
    l.s $f0, -56($fp)               # float13 = float11 / float12
    l.s $f2, -60($fp)
    div.s $f4, $f0, $f2
    s.s $f4, -64($fp)
    l.s $f0, -64($fp)               # z = float13
    s.s $f0, -20($fp)
    l.s $f0, -20($fp)               # float14 = z
    s.s $f0, -68($fp)
    l.s $f12, -68($fp)              # write float14
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_1                   # string15 = "Hola MIPS"
    sw $t0, -76($fp)
    lw $t0, -76($fp)                # saludo = string15
    sw $t0, -72($fp)
    lw $t0, -72($fp)                # string16 = saludo
    sw $t0, -80($fp)
    lw $a0, -80($fp)                # write string16
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_2                   # string17 = "fin de textos"
    sw $t0, -84($fp)
    lw $a0, -84($fp)                # write string17
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, -4($fp)                # float18 = x
    s.s $f0, -88($fp)
    l.s $f0, -12($fp)               # float19 = y
    s.s $f0, -92($fp)
    l.s $f0, -88($fp)               # bool20 = float18 > float19
    l.s $f2, -92($fp)
    c.lt.s $f2, $f0
    li $t2, 1
    bc1t cmp_listo_1
    li $t2, 0

cmp_listo_1:
    sw $t2, -96($fp)
    lw $t0, -96($fp)                # if bool20 goto principal$if$1$bloque1
    bnez $t0, principal.if.1.bloque1
    j principal.if.1.siguiente1     # goto principal$if$1$siguiente1

principal.if.1.bloque1:
    la $t0, txt_3                   # string21 = "x es mayor"
    sw $t0, -100($fp)
    lw $a0, -100($fp)               # write string21
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.1.siguiente1:

principal.if.1.final:
    li $t0, 65                      # char22 = 'A'
    sw $t0, -108($fp)
    lw $t0, -108($fp)               # letra = char22
    sw $t0, -104($fp)
    lw $t0, -104($fp)               # char23 = letra
    sw $t0, -112($fp)
    li $t0, 65                      # char24 = 'A'
    sw $t0, -116($fp)
    lw $t0, -112($fp)               # bool25 = char23 == char24
    lw $t1, -116($fp)
    seq $t2, $t0, $t1
    sw $t2, -120($fp)
    lw $t0, -120($fp)               # if bool25 goto principal$if$2$bloque1
    bnez $t0, principal.if.2.bloque1
    j principal.if.2.siguiente1     # goto principal$if$2$siguiente1

principal.if.2.bloque1:
    la $t0, txt_4                   # string26 = "la letra es A"
    sw $t0, -124($fp)
    lw $a0, -124($fp)               # write string26
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.2.siguiente1:

principal.if.2.final:
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
