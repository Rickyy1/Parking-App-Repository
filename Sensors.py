import RPi.GPIO as GPIO
import time
import requests
import threading

beam_enter_script = 'https://rwuparking.rwu.me/parking_enter.php/?auth=password&lot_name=Lot A'
beam_exit_script = 'https://rwuparking.rwu.me/parking_exit.php/?auth=password&lot_name=Lot A'

ENTER_BEAM_PIN = 23
EXIT_BEAM_PIN = 17
cooldown = 2
last_beam_broken_time = 0
last_beam_broken_time_exit = 0 

def break_beam_callback(channel):
    global last_beam_broken_time
    
    current_time = time.time()
    if GPIO.input(ENTER_BEAM_PIN):
        
        print("\n")
        
    else:
        
        if current_time - last_beam_broken_time > cooldown:
            print("+1 car\n")
            last_beam_broken_time = current_time
            
            try:
                response = requests.post(beam_enter_script)
                if response.status_code == 200:
                    print("Successful")
                else:
                    print("Unsuccessful")    
            except Exception as e:
                print("Error")        
            
        #else:
            #print("Cooldown active. Ignoring beam.")

def break_beam_callback_exit(channel):
    global last_beam_broken_time_exit
    current_time = time.time()
    if GPIO.input(EXIT_BEAM_PIN):
        
        print("\n")
        
    else:
        
        if current_time - last_beam_broken_time_exit > cooldown:
            print("-1 car\n")
            last_beam_broken_time_exit = current_time
            
            try:
                response = requests.post(beam_exit_script)
                if response.status_code == 200:
                    print("Successful")
                else:
                    print("Unsuccessful")    
            except Exception as e:
                print("Error")        
            
        #else:
            #print("Cooldown active. Ignoring beam.")
            
GPIO.setmode(GPIO.BCM)
GPIO.setup(ENTER_BEAM_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(EXIT_BEAM_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.add_event_detect(ENTER_BEAM_PIN, GPIO.BOTH, callback=break_beam_callback)
GPIO.add_event_detect(EXIT_BEAM_PIN, GPIO.BOTH, callback=break_beam_callback_exit)


car_parked_script = 'https://rwuparking.rwu.me/desig_parked_param.php/?auth=password&lot_name=Lot A'
car_left_script =   'https://rwuparking.rwu.me/desig_left_param.php/?auth=password&lot_name=Lot A'


# Initialize GPIO
GPIO.setmode(GPIO.BCM)

# Define sensor pins
SENSOR_PINS = [18, 27]  # Example sensor pins, adjust as needed

# Setup sensor pins
for pin in SENSOR_PINS:
    GPIO.setup(pin, GPIO.IN)

car_states = {pin: False for pin in SENSOR_PINS}

def check_sensor_for_duration(sensor_pin, duration):
    start_time = time.time()
    while time.time() - start_time < duration:
        if not detect_obstacle(sensor_pin):
            return False
        time.sleep(0.1)  # Adjust sleep duration as needed for responsiveness
    return True
    
def detect_obstacle(pin):
    return GPIO.input(pin) == GPIO.LOW

def check_sensor(sensor_pin, car_parked_script, car_left_script):
    global car_states
    duration = 3
    duration2 = 3
    while True:
        if detect_obstacle(sensor_pin):
            if not car_states[sensor_pin]:
                if check_sensor_for_duration(sensor_pin, duration):
                    print("Vehicle parked on pin", sensor_pin)
                    car_states[sensor_pin] = True
                    response = requests.post(car_parked_script)
                    if response.status_code == 200:
                        print("Successful")
                    else:
                        print("Unsuccessful")
                else:
                    car_states[sensor_pin] = False
                    
        elif car_states[sensor_pin]:
            # Start a timer for vehicle leaving
            leaving_start_time = time.time()
            while time.time() - leaving_start_time < duration2:
                # If the obstacle is detected again within the leaving duration,
                # break out of the timer loop and continue monitoring
                if detect_obstacle(sensor_pin):
                    break
                time.sleep(0.1)
            else:
                # If the leaving duration elapses without obstacle detection,
                # mark the vehicle as left
                print("Vehicle left on pin", sensor_pin)
                response = requests.post(car_left_script)
                car_states[sensor_pin] = False
                if response.status_code == 200:
                    print("Successful")
                else:
                    print("Unsuccessful")

try:
    threads = []
    for pin in SENSOR_PINS:
        t = threading.Thread(target=check_sensor, args=(pin, car_parked_script, car_left_script))
        threads.append(t)
        t.start()

    # Main thread waits for all threads to finish
    for t in threads:
        t.join()

except KeyboardInterrupt:
    print("Program stopped by user")

finally:
    GPIO.cleanup()

