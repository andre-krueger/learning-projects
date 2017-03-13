#!/usr/bin/env python
from sys import argv
import calcgui
from PyQt5.QtWidgets import QApplication, QMainWindow
from sys import exit


class CalculatorGui(QMainWindow):

    def __init__(self):
        super(CalculatorGui, self).__init__()
        self.expression = ""
        self.ui = calcgui.Ui_MainWindow()
        self.ui.setupUi(self)
        self.calc = Calculator()

        # numbers
        self.ui.pushButton_0.clicked.connect(
            lambda: self.ui.lineEdit.insert("0")
        )
        self.ui.pushButton_1.clicked.connect(
            lambda: self.ui.lineEdit.insert("1")
        )
        self.ui.pushButton_2.clicked.connect(
            lambda: self.ui.lineEdit.insert("2")
        )

        self.ui.pushButton_3.clicked.connect(
            lambda: self.ui.lineEdit.insert("3")
        )

        self.ui.pushButton_4.clicked.connect(
            lambda: self.ui.lineEdit.insert("4")
        )

        self.ui.pushButton_5.clicked.connect(
            lambda: self.ui.lineEdit.insert("5")
        )

        self.ui.pushButton_6.clicked.connect(
            lambda: self.ui.lineEdit.insert("6")
        )

        self.ui.pushButton_7.clicked.connect(
            lambda: self.ui.lineEdit.insert("7")
        )

        self.ui.pushButton_8.clicked.connect(
            lambda: self.ui.lineEdit.insert("8")
        )

        self.ui.pushButton_9.clicked.connect(
            lambda: self.ui.lineEdit.insert("9")
        )

        # others
        self.ui.pushButton_dot.clicked.connect(
            lambda: self.ui.lineEdit.insert(".")
        )
        self.ui.pushButton_del.clicked.connect(
            lambda: self.ui.lineEdit.clear()
        )

        # operations
        self.ui.pushButton_plus.clicked.connect(
            lambda: self.ui.lineEdit.insert("+"))
        self.ui.pushButton_minus.clicked.connect(
            lambda: self.ui.lineEdit.insert("-")
        )
        self.ui.pushButton_mult.clicked.connect(
            lambda: self.ui.lineEdit.insert("*")
        )
        self.ui.pushButton_div.clicked.connect(
            lambda: self.ui.lineEdit.insert("/")
        )
        self.ui.pushButton_equal.clicked.connect(
            lambda: self.ui.lineEdit.setText(
                str(self.calc.calculate(self.ui.lineEdit.text())))
        )


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

    def calculate(self, expression):
        stack = []
        expression = InfixParser.parse(expression)
        for token in expression.split():
            if token.split(".")[0].isnumeric():
                stack.append(token)
            elif token.split(".")[0] == "":
                stack.append("0" + token)
            else:
                if len(stack) > 1:
                    left_operand = float(stack.pop())
                    right_operand = float(stack.pop())
                    stack.append(self.operations[token][0](
                        right_operand, left_operand))
        if stack:
            return stack.pop()
        return "Invalid Expression"


class InfixParser():

    def parse(expression):

        postfix = ""
        stack = []
        for token in expression.split():
            for i in token:
                if i.isdigit() or i == ".":
                    postfix += i
                else:
                    postfix += " "
                    while stack and Calculator.operations[stack[-1]][1] \
                            >= Calculator.operations[i][1]:
                        postfix += stack.pop() + " "
                    stack.append(i)
        while stack:
            postfix += " " + stack.pop()
        return postfix


def main():
    if len(argv) > 1:
        c = Calculator()
        print(c.calculate(argv[-1]))
    else:
        app = QApplication(argv)
        c = CalculatorGui()
        c.show()
        exit(app.exec_())


if __name__ == "__main__":
    main()
