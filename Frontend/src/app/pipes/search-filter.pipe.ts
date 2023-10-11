import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(tableList:any, searchtable: string): any {
    if(!tableList || !searchtable){
      return tableList;
    }
    return tableList.filter(table =>
      table.toLocaleLowerCase().includes(searchtable.toLocaleLowerCase()));
  }
  transform1(collist:any, searchcol: string): any {
    if(!collist || !searchcol){
      return collist;
    }
    return collist.filter(table =>
      table.toLocaleLowerCase().includes(searchcol.toLocaleLowerCase()));
  }
transform2(querydata:any, searchquery: string): any{
  if(!querydata || !searchquery){
    return querydata;
  }
  return querydata.filter(table =>
    table.toLocaleLowerCase().includes(searchquery.toLocaleLowerCase()));
}
}
