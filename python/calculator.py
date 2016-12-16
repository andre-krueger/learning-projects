#!/usr/bin/env python
from sys import argv


class Calculator():

    def __init__(self, expression):
        self.expression = expression
        self.operand_stack = []
        self.operator_stack = []
        self.operations = {
            '+': [lambda a, b: a + b, 2],
            '*': [lambda a, b: a * b, 1]
        }

    def calculate(self):
        result = 0
        for token in self.expression:
            if token.isdigit():
                self.operand_stack.append(float(token))
            elif token == "+" or token == "*":
                if self.operator_stack and \
                   self.operations[self.operator_stack[-1]][1]\
                   <= self.operations[token][1]:
                    operator = self.operator_stack.pop()
                    right_operand = self.operand_stack.pop()
                    left_operand = self.operand_stack.pop()
                    result = self.operations[operator][
                        0](left_operand, right_operand)
                    self.operand_stack.append(result)
                self.operator_stack.append(token)

        print(result)


def main():
    c = Calculator(argv[-1])
    c.calculate()


if __name__ == "__main__":
    main()
