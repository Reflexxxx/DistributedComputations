package main;

import (
	"fmt"
	"math/rand"
	"time"
)

//var requestSemaphore = make(chan int, maxOpenRequests) // Integer chanel with a maximum queue size
var requestSemaphore = make(chan int, 1);


// Tobacco, paper, matches, component



type Table struct {
	component1 string;
	component2 string;
}

//type DifferComponentsGenerator struct {
//	s rand.Source;
//	r rand.Rand;
//
//	//s1 := rand.NewSource(time.Now().UnixNano())
//	//r1 := rand.New(s1)
//}
//
//func NewDifferComponentGenerator() *DifferComponentsGenerator {
//	s1 := rand.NewSource(time.Now().UnixNano())
//	//r1 := rand.New(s1)
//	return &DifferComponentsGenerator{rand.NewSource(time.Now().UnixNano()), rand.New(s1)}
//}


func main() {

	table := Table{"", ""}

	tookComponentsOffTable := make(chan bool, 1);

	isWorking := true;
	workIsDone := make(chan bool, 1)

	go Intermediator(&table, tookComponentsOffTable, workIsDone, &isWorking);
	go Smoker("tabacco", &table, tookComponentsOffTable, &isWorking);
	go Smoker("paper", &table, tookComponentsOffTable, &isWorking);
	go Smoker("matches", &table, tookComponentsOffTable, &isWorking);

	<-workIsDone
}

func fill2DifferRandomComponents(table *Table, r *rand.Rand) {
	x := r.Intn(3);
	switch (x) {
	case 0: {
		table.component1 = "tabacco";
		table.component2 = "paper";
		//fmt.Println("generated components are: tabacco and paper");
		//break;
	}
	case 1: {
		table.component1 = "tabacco";
		table.component2 = "matches";
		//fmt.Println("generated components are: tabacco and matches");
		//break;
	}
	case 2: {
		table.component1 = "paper";
		table.component2 = "matches";
		//fmt.Println("generated components are: paper and matches");
		//break;
	}

	}
}


func Intermediator(table *Table, tookComponentsOffTable chan bool, workIsDone chan bool, isWorking *bool)  {
	s := rand.NewSource(time.Now().UnixNano())
	r := rand.New(s)
	//var isWorking = true;
	for i := 0; i < 20; i++ {
		requestSemaphore <- 1;
			fill2DifferRandomComponents(table, r);
			fmt.Println("Mediator put " + table.component1 + " and " + table.component2 + " on the table");
		<- requestSemaphore;
		<- tookComponentsOffTable
	}
	*isWorking = false;
	fmt.Println("Mediator finished his work.")
	workIsDone <- true;
}

func Smoker(ownComponent string, table *Table, tookComponentsOffTable chan bool, isWorking *bool) {
	//var isWorking = true;
	for (*isWorking) {
		//fmt.Println("in Smoker with " + ownComponent + ".");
		requestSemaphore <- 1;
		// Components must be different among themselves
		// If they are different from our component, then we think that we have 3 different components
		// and we can twist the cigarette and smoke it
		//fmt.Println("inside smoker with " + ownComponent + "; components are: '" + table.component1 + "' and '" + table.component2 + "'");
		if (table.component1 != "" && table.component1 != ownComponent && table.component2 != ownComponent) {
			fmt.Println("Smoker with " + ownComponent + " twisted the cigarette and smokes it.");
			table.component1 = "";
			table.component2 = "";
			tookComponentsOffTable <- true;
		}
		<-requestSemaphore;
	}

	fmt.Println("Smoker with " + ownComponent + " ended his work.");
}

