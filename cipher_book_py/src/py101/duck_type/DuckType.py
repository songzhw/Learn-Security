class Duck():
    def sound(self):
        print("quack")

class Cat():
    def sound(self):
        print("meow")

def talkTo(animal):
    animal.sound()

if __name__ == '__main__':
    kitty = Cat()
    duck = Duck()
    talkTo(kitty)
    talkTo(duck)