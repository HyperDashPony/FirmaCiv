def navClockBaseModel():
    templatefile = open("nav_clock.json", "r")
    tfdata = templatefile.read()

    tfdata = tfdata.replace("item/clock", 'firmaciv:item/nav_clock/nav_clock')

    outfile = open("nav_clock.json", 'w')

    outfile.write(tfdata)

    templatefile.close()

def navClockModels():

    for i in range(0, 64):
        templatefile = open("nav_clock_00.json", "r")
        tfdata = templatefile.read()

        if(i < 10):
            tfdata = tfdata.replace("nav_clock_00", ('nav_clock_0' + str(i)))
            outfile = open("nav_clock_0" + str(i) +".json", 'w')
        else:
            tfdata = tfdata.replace("nav_clock_00", ('nav_clock_' + str(i)))
            outfile = open("nav_clock_" + str(i) +".json", 'w')

        outfile.write(tfdata)

        templatefile.close()

def replaceTime():
    templatefile = open("nav_clock.json", "r")
    tfdata = templatefile.read()

    tfdata = tfdata.replace("time", 'firmaciv:pm_time')

    outfile = open("nav_clock.json", 'w')

    outfile.write(tfdata)

    templatefile.close()

replaceTime();



