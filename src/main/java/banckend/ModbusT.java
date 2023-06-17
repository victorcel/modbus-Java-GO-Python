package banckend;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;

public class ModbusT {
    public static void main(String[] args) {
        ModbusT modbus = new ModbusT();
        System.out.println("Arranco");

            modbus.prueba();

    }

    public void prueba()  {
        String port = "/dev/cu.usbserial-14620";
        int dataBits = 8;
        int stopBits = 1;
        int slaveId = 2;
        int startingAddress = 0;
        int quantity = 10;


        // Crear el maestro Modbus RTU
        ModbusMaster modbusMaster = null;

        try {
            // Configurar los parámetros de la conexión serie
            SerialParameters serialParameters = new SerialParameters();
            serialParameters.setDevice(port);
            serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_4800);
            serialParameters.setDataBits(dataBits);
            serialParameters.setParity(SerialPort.Parity.NONE);
            serialParameters.setStopBits(stopBits);

            modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
            // Abrir la conexión
            modbusMaster.connect();

            // Leer los registros de retención
            int[] results = modbusMaster.readHoldingRegisters(slaveId, startingAddress, quantity);

            // Imprimir los resultados en formato decimal
            for (int result : results) {
                System.out.println("Valor decimal: " + result);
            }
        } catch (ModbusNumberException | ModbusProtocolException | SerialPortException |ModbusIOException e) {
            e.printStackTrace();
        }
    }
}
