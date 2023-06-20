import time

import minimalmodbus

import serial

for i in range(50):
    instrument = minimalmodbus.Instrument('/dev/cu.usbserial-14620', 2,
                                          debug=True)  # note 0 as broadcast adress
    instrument.serial.bytesize = 8
    instrument.serial.baudrate = 4800
    instrument.serial.stopbits = 1
    instrument.serial.parity = serial.PARITY_NONE
    instrument.serial.timeout = 1

    address = instrument.read_registers(registeraddress=0, number_of_registers=10)
    print(f"device address is {address}")

    instrument.serial.close()
    time.sleep(2)
