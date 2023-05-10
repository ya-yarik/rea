function flipFlop(flipFlop)
    {element = document.getElementById(flipFlop);
        if(element)
        element.style.display = element.style.display == "none" ? "" : "none";
    }