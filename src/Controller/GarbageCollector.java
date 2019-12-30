package Controller;

import Model.Collection.IHeap;
import Model.ProgramState;
import Model.Values.IValue;
import Model.Values.ReferenceValue;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

class GarbageCollector {

    Map<Integer, IValue> safeGarbageCollector(List<Integer> symbolTableAdr, IHeap heap)
    {
        Set<Map.Entry<Integer,IValue>> heapSet=heap.getEntrySet();

        return heapSet.stream().filter(e->symbolTableAdr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    List<Integer> getAddressFromSymbolTable(List<IValue> content)
    {
        return content.stream().filter(element->element instanceof ReferenceValue)
                .map(element->((ReferenceValue)element).getAddress()).collect((Collectors.toList()));
    }

    List<Integer> getAddressFromTables(List<ProgramState> programs){
        return programs.stream()
                .flatMap(programState -> programState.getSymTable().values().stream())
                //.flatMap(Function.identity())
                .collect(Collectors.toList())
                .stream()
                .filter(element -> element instanceof  ReferenceValue)
                .map(element -> ((ReferenceValue) element).getAddress())
                .collect(Collectors.toList());
    }

    List<Integer> addIndirections(List<Integer> addressesFromSymbolTable, IHeap heapTable){
        boolean change = true;
        //get entry set that needs modifications
        Set<Map.Entry<Integer, IValue>> heapSet = heapTable.getEntrySet();
        //copy of list in order to add indirections
        List<Integer> newAddressList = addressesFromSymbolTable.stream().collect(Collectors.toList());

        // go through heapSet again and again and each time we add to the address list new indirection level and new addresses which must NOT be deleted
        while (change){
            List<Integer> appendingList = null;
            change = false;
            appendingList = heapSet.stream()
                    //check if val in heap is RefValue so it can have indirections
                    .filter(e -> e.getValue() instanceof ReferenceValue)
                    //check if address list contains ref to this
                    .filter(e -> newAddressList.contains(e.getKey()))
                    //map the reference to its address so we can add it
                    .map(e -> (((ReferenceValue) e.getValue()).getAddress()))
                    //check if the address list already has that reference from prev elements
                    .filter(e -> !newAddressList.contains(e))
                    //collect to list
                    .collect(Collectors.toList());

            if(!appendingList.isEmpty()){
                //still have indirect references so we have to keep checking
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }

}