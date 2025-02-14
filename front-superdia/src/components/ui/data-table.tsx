
import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  getSortedRowModel,
  SortingState,
  useReactTable,
} from "@tanstack/react-table"

import { ArrowDown, ArrowUp } from "lucide-react"
import { Dispatch, SetStateAction } from "react"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "./table"

interface DataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[]
  data: TData[]
  handleDelete: (id: string) => void
  sorting: SortingState
	setSorting: Dispatch<SetStateAction<SortingState>>
}

export function DataTable<TData, TValue>({
  columns,
  data,
  handleDelete,
  sorting,
  setSorting
}: DataTableProps<TData, TValue>) {
  

  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    state: {
      sorting,
    },
    onSortingChange: setSorting,
    getSortedRowModel: getSortedRowModel(),
    meta: {
      handleDelete
    }
  })

  return (
    <div className="rounded-md border bg-sky-200 shadow-md">
      <Table className="overflow-hidden">
        <TableHeader>
          {table.getHeaderGroups().map((headerGroup) => (
            <TableRow key={headerGroup.id}>
              {headerGroup.headers.map((header) => {
                return (
                  <TableHead key={header.id}>
                    {header.isPlaceholder ? null : (
                        <div
                          {...{
                            className: header.column.getCanSort()
                              ? "select-none cursor-pointer text-black"
                              : "cursor-default text-black",
                            onClick: header.column.getToggleSortingHandler(),
                          }}
                        >
                          {/* {flexRender(header.column.columnDef.header, header.getContext())} */}
                          {header.column.columnDef.id === "add-contact" ? (
                            <div className="flex flex-row items-center">
                              {flexRender(header.column.columnDef.header, header.getContext())}
                            </div>
                          ) : (
                            <div className="flex flex-row space-x-1 whitespace-nowrap">
                              <div>{flexRender(header.column.columnDef.header, header.getContext())}</div>
                              <div>
                                {{
                                  asc: <ArrowDown className="h-4 w-4" />,
                                  desc: <ArrowUp className="h-4 w-4" />,
                                }[header.column.getIsSorted() as string] ?? null}
                              </div>
                            </div>
                          )}
                        </div>
                  )}
                  </TableHead>
                )
              })}
            </TableRow>
          ))}
        </TableHeader>
        <TableBody>
          {table.getRowModel().rows?.length ? (
            table.getRowModel().rows.map((row) => (
              <TableRow
                key={row.id}
                data-state={row.getIsSelected() && "selected"}
              >
                {row.getVisibleCells().map((cell) => (
                  <TableCell key={cell.id}>
                    {flexRender(cell.column.columnDef.cell, cell.getContext())}
                  </TableCell>
                ))}
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={columns.length} className="h-24 text-center">
                No results.
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  )
}
