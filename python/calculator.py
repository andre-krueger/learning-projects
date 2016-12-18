#!/usr/bin/env python
from sys import argv


class Calculator():
    """
    A primitive calculator
    """
    operations = {
        '+': [lambda a, b: a + b, 1],
        '*': [lambda a, b: a * b, 2],
        '-': [lambda a, b: a - b, 1],
        '/': [lambda a, b: a / b, 2],
    }

    def __init__(self, expression):
        self.expression = expression

        self.postfix = InfixParser.parse(self.expression)

    def calculate(self):
        stack = []
        for token in self.postfix.split():
            if token.isdigit():
                stack.append(token)
            else:
                left_operand = float(stack.pop())
                right_operand = float(stack.pop())
                stack.append(self.operations[token][0](
                    right_operand, left_operand))

        return stack.pop()


class InfixParser():

    def parse(expression):

        postfix = ""
        stack = []
        for token in expression.split():
            for i in token:
                if i.isdigit():
                    postfix += i
                else:
                    postfix += " "
                    while stack and Calculator.operations[stack[-1]][1] \
                            >= Calculator.operations[i][1]:
                        postfix += stack.pop()
                    stack.append(i)
        while stack:
            postfix += " " + stack.pop()
        return postfix


def main():
    c = Calculator(argv[-1])
    print(c.calculate())


if __name__ == "__main__":
    main()
