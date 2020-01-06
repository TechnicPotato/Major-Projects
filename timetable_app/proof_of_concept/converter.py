# Converter form a iCalendar format.
def load_data(filename):
    with open(filename, "r") as f:
        file_data = f.read()
        return file_data
    # If read fails
    print("Failed to read file")
    return None



if __name__ == "__main__":
    pass