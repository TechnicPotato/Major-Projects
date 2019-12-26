import yaml

def user_input():
    print("Inputting new entry.")
    name = input("Input name:\t")
    author = input("Input author:\t")
    year = input("Input year:\t")
    tags = input("Input tags, seperated by commas:\n").split(",")
    text = input("Input text:\n")
    data = dict(
        name = dict(
            author = author,
            year = year,
            tags = tags,
            text = text,
        )
    )
    # Replace name with actual name of string
    data[str(name)] = data['name']
    del data['name']
    print(data)

if __name__ == "__main__":
    user_input()