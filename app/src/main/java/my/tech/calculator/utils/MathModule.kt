package my.tech.calculator.utils

import java.text.ParseException
import kotlin.math.pow

class MathModule(expression: String) {

    private val lexemeBuffer: LexemeBuffer

    init {
        lexemeBuffer = LexemeBuffer(lexAnalyze(expression))
    }

    enum class LexemeType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_PLUS, OP_MINUS, OP_MUL, OP_DIV, OP_SQRT, OP_SQR, OP_PERCENT,
        NUMBER,
        EOF
    }

    class Lexeme {
        val type: LexemeType
        val value: String

        constructor(type: LexemeType, value: String) {
            this.type = type
            this.value = value
        }

        constructor(type: LexemeType, value: Char) {
            this.type = type
            this.value = value.toString()
        }
    }

    class LexemeBuffer(val lexemes: List<Lexeme>) {
        private var position: Int = 0

        fun next(): Lexeme {
            return lexemes[position++]
        }

        fun back() {
            position--
        }

        fun getPosition(): Int {
            return position
        }
    }

    private fun lexAnalyze(expression: String): List<Lexeme> {
        val lexemes = arrayListOf<Lexeme>()
        var position = 0

        while (position < expression.length) {
            var char = expression[position]

            when (char) {
                '(' -> {
                    lexemes.add(Lexeme(LexemeType.LEFT_BRACKET, char))
                    position++
                }
                ')' -> {
                    lexemes.add(Lexeme(LexemeType.RIGHT_BRACKET, char))
                    position++
                }
                '+' -> {
                    lexemes.add(Lexeme(LexemeType.OP_PLUS, char))
                    position++
                }
                '-' -> {
                    lexemes.add(Lexeme(LexemeType.OP_MINUS, char))
                    position++
                }
                '*' -> {
                    lexemes.add(Lexeme(LexemeType.OP_MUL, char))
                    position++
                }
                '/' -> {
                    lexemes.add(Lexeme(LexemeType.OP_DIV, char))
                    position++
                }
                '√' -> {
                    lexemes.add(Lexeme(LexemeType.OP_SQRT, char))
                    position++
                }
                '^' -> {
                    lexemes.add(Lexeme(LexemeType.OP_SQR, char))
                    position++
                }
                '%' -> {
                    lexemes.add(Lexeme(LexemeType.OP_PERCENT, char))
                    position++
                }
                else -> {
                    if (char.isDigit() || char == '.') {
                        val stringBuilder = StringBuilder()
                        do {
                            stringBuilder.append(char)
                            position++
                            if (position >= expression.length) {
                                break
                            }
                            char = expression[position]
                        } while (char.isDigit() || char == '.')
                        lexemes.add(Lexeme(LexemeType.NUMBER, stringBuilder.toString()))
                    } else {
                        throw ParseException("Unexpected character: $char", position)
                    }
                }
            }
        }
        lexemes.add(Lexeme(LexemeType.EOF, ""))
        return lexemes
    }

    fun calculate(): Double {
        val lexeme = this.lexemeBuffer.next()
        return if (lexeme.type == LexemeType.EOF) {
            0.0
        } else {
            this.lexemeBuffer.back()
            plusminus(this.lexemeBuffer)
        }
    }

    private fun plusminus(lexemes: LexemeBuffer): Double {
        var value = muldiv(lexemes)
        while (true) {
            val lexeme = lexemes.next()
            when (lexeme.type) {
                LexemeType.OP_MINUS -> {
                    value -= muldiv(lexemes)
                }
                LexemeType.OP_PLUS -> {
                    value += muldiv(lexemes)
                }
                LexemeType.EOF -> {
                    lexemes.back()
                    return value
                }
                LexemeType.RIGHT_BRACKET -> {
                    lexemes.back()
                    return value
                }
                else -> {
                    throw ParseException(
                        "Unexpected token ${lexeme.value} at position ${lexemes.getPosition()}",
                        lexemes.getPosition()
                    )
                }
            }
        }
    }

    private fun muldiv(lexemes: LexemeBuffer): Double {
        var value = factor(lexemes)
        while (true) {
            val lexeme = lexemes.next()
            when (lexeme.type) {
                LexemeType.OP_MUL -> {
                    value *= factor(lexemes)
                }
                LexemeType.OP_DIV -> {
                    value /= factor(lexemes)
                }
                LexemeType.OP_PERCENT -> {
                    value %= factor(lexemes)
                }
                LexemeType.OP_SQR -> {
                    value = value.pow(factor(lexemes))
                }
                LexemeType.EOF -> {
                    lexemes.back()
                    return value
                }
                LexemeType.RIGHT_BRACKET -> {
                    lexemes.back()
                    return value
                }
                LexemeType.OP_MINUS -> {
                    lexemes.back()
                    return value
                }
                LexemeType.OP_PLUS -> {
                    lexemes.back()
                    return value
                }
                else -> {
                    throw ParseException(
                        "Unexpected token ${lexeme.value} at position ${lexemes.getPosition()}",
                        lexemes.getPosition()
                    )
                }
            }
        }
    }

    private fun factor(lexemes: LexemeBuffer): Double {
        var lexeme = lexemes.next()
        when (lexeme.type) {
            LexemeType.NUMBER -> {
                val value = lexeme.value.toDouble()
                if (value.isInfinite()) {
                    throw ParseException("Error", lexemes.getPosition())
                }
                return value
            }
            LexemeType.LEFT_BRACKET -> {
                val value = plusminus(lexemes)
                lexeme = lexemes.next()
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw ParseException(
                        "Unexpected token ${lexeme.value} at position ${lexemes.getPosition()}",
                        lexemes.getPosition()
                    )
                }
                return value
            }
            LexemeType.OP_SQRT -> {
                return kotlin.math.sqrt(factor(lexemes))
            }
            LexemeType.OP_MINUS -> {
                return -factor(lexemes)
            }
            LexemeType.OP_PLUS -> {
                return factor(lexemes)
            }
            else -> {
                throw ParseException(
                    "Unexpected token ${lexeme.value} at position ${lexemes.getPosition()}",
                    lexemes.getPosition()
                )
            }
        }
    }


}