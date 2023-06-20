package main

import (
	"fmt"
	"log"
	"strconv"
	"time"

	"github.com/goburrow/modbus"
)

func main() {
	handler := modbus.NewRTUClientHandler("/dev/cu.usbserial-14620")
	handler.BaudRate = 2400
	handler.DataBits = 8
	handler.Parity = "N"
	handler.StopBits = 1
	handler.SlaveId = 2
	handler.Timeout = 5 * time.Second

	//handler.Logger = log.New(os.Stdout, "ascii: ", log.LstdFlags)
	client := modbus.NewClient(handler)
	//
	//for {
	results, err := client.ReadHoldingRegisters(0, 10)
	if err != nil {
		log.Fatal(err)
	}
	time.Sleep(2 * time.Second)
	hexValue := ""
	for _, result := range results {
		hexValue += fmt.Sprintf("%02X", result)

	}
	//fmt.Println(hexValue)
	// Dividir en grupos de 4 caracteres
	chunks := make([]string, 0)

	for i := 0; i < len(hexValue); i += 4 {
		end := i + 4
		if end > len(hexValue) {
			end = len(hexValue)
		}
		chunk := hexValue[i:end]
		chunks = append(chunks, chunk)
	}

	// Imprimir los grupos de 4 caracteres
	//for _, chunk := range chunks {
	decValue, _ := strconv.ParseInt(chunks[6], 16, 32)
	fmt.Printf(" %d\n", decValue)
	//}
}
